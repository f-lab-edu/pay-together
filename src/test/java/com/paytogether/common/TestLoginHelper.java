package com.paytogether.common;

import com.paytogether.auth.service.LoginService;
import com.paytogether.member.entity.Gender;
import com.paytogether.member.entity.Member;
import com.paytogether.member.repository.MemberRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class TestLoginHelper {

  private final LoginService loginService;
  private final PasswordEncoder passwordEncoder;
  private final MemberRepository memberRepository;

  public Member loginAsTestUser() {
    String password = "1234!678";
    String email = "pay@spring.com";
    Member member = Member.createMember(email, passwordEncoder.encode(password), "name", 100,
        Gender.MALE,
        "address", "01012345678", LocalDateTime.now());

    memberRepository.save(member);
    loginService.login(email, password);
    return member;
  }
}