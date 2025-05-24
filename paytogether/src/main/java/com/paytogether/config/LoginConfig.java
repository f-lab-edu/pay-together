package com.paytogether.config;

import com.paytogether.domain.member.repository.MemberRepository;
import com.paytogether.domain.member.service.LoginService;
import com.paytogether.domain.member.service.SessionLoginService;
import jakarta.servlet.http.HttpSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class LoginConfig {

  @Bean
  public LoginService loginService(
      MemberRepository memberRepository,
      PasswordEncoder passwordEncoder,
      HttpSession httpSession) {
    return new SessionLoginService(memberRepository, passwordEncoder, httpSession);
  }
}
