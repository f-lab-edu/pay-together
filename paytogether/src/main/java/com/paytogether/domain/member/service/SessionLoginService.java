package com.paytogether.domain.member.service;

import com.paytogether.domain.member.entity.Member;
import com.paytogether.domain.member.repository.MemberRepository;
import jakarta.servlet.http.HttpSession;
import java.util.Optional;
import org.springframework.security.crypto.password.PasswordEncoder;

public class SessionLoginService implements LoginService {

  private final MemberRepository memberRepository;
  private final PasswordEncoder passwordEncoder;
  private final HttpSession httpSession;
  public static final String LOGIN_MEMBER = "LOGIN_MEMBER";

  public SessionLoginService(MemberRepository memberRepository, PasswordEncoder passwordEncoder,
      HttpSession httpSession) {
    this.memberRepository = memberRepository;
    this.passwordEncoder = passwordEncoder;
    this.httpSession = httpSession;
  }

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
    httpSession.setAttribute(LOGIN_MEMBER, presentMember.getId());
  }

  @Override
  public void logout() {
    httpSession.invalidate();
  }

  @Override
  public boolean sessionExists(HttpSession session) {
    Object loginMemberId = session.getAttribute(LOGIN_MEMBER);
    if (loginMemberId != null) {
      return true;
    }
    return false;
  }
}
