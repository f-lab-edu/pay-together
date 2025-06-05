package com.paytogether.member.service;

import com.paytogether.exception.PayTogetherErrors;
import com.paytogether.exception.PayTogetherException;
import com.paytogether.member.api.response.LoginResponse;
import com.paytogether.member.entity.Member;
import com.paytogether.member.repository.MemberRepository;
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
  public LoginResponse login(String email, String rawPassword) {
    Optional<Member> optionalMember = memberRepository.findByEmail(email);
    if (optionalMember.isEmpty()) {
      throw new PayTogetherException(PayTogetherErrors.INVALID_INFO);
    }

    Member presentMember = optionalMember.get();
    String encodedPassword = presentMember.getPassword();
    if (!passwordEncoder.matches(rawPassword, encodedPassword)) {
      throw new PayTogetherException(PayTogetherErrors.INVALID_INFO);
    }

    boolean duplicateLogin = false;
    if (sessionExists()) {
      httpSession.invalidate();
      duplicateLogin = true;
    }

    httpSession.setAttribute(LOGIN_MEMBER, presentMember.getId());
    log.info("Session ID = {}, Member ID = {}", httpSession.getId(), presentMember.getId());
    return LoginResponse.of(presentMember.getEmail(), duplicateLogin);
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
