package com.paytogether.domain.member.service;

import com.paytogether.domain.member.entity.Member;
import com.paytogether.domain.member.repository.MemberRepository;
import jakarta.servlet.http.HttpSession;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class SessionLoginService implements LoginService {

  public static final String LOGIN_MEMBER = "LOGIN_MEMBER";
  private final MemberRepository memberRepository;
  private final PasswordEncoder passwordEncoder;
  private final HttpSession httpSession;

  @Override
  public void login(String email, String rawPassword) {
    Optional<Member> optionalMember = memberRepository.findByEmail(email);
    if (optionalMember.isEmpty()) {
      throw new IllegalArgumentException();
    }

    Member presentMember = optionalMember.get();
    String encodedPassword = presentMember.getPassword();
    if (!passwordEncoder.matches(rawPassword, encodedPassword)) {
      throw new IllegalArgumentException();
    }

    if (sessionExists()) {
      httpSession.invalidate();
    }

    httpSession.setAttribute(LOGIN_MEMBER, presentMember.getId());
    log.info("Session ID = {}, Member ID = {}", httpSession.getId(), presentMember.getId());
  }

  @Override
  public void logout() {
    httpSession.invalidate();
  }

  @Override
  public boolean sessionExists() {
    Object loginMemberId = httpSession.getAttribute(LOGIN_MEMBER);
    return Optional.ofNullable(loginMemberId).isPresent();
  }
}
