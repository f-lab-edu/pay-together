package com.paytogether.domain.member.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.paytogether.domain.member.entity.Gender;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class MemberServiceTest {

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private MemberService memberService;

  @Test
  void join_success() {
    MemberJoinRequest request = MemberJoinRequest.builder()
        .age(100)
        .name("pay")
        .gender(Gender.MALE)
        .address("seoul")
        .password("1234!678")
        .email("pay@spring.com")
        .phoneNumber("01012345678")
        .build();

    MemberJoinResponse result = memberService.join(request);

    assertThat(request.getEmail()).isEqualTo(result.getEmail());
  }

  @Test
  void passwordEncoder_success() {
    String password = "123456!89";
    String encodedPassword = passwordEncoder.encode(password);

    assertThat(password).isNotEqualTo(encodedPassword);
    assertThat(passwordEncoder.matches(password, encodedPassword)).isTrue();
  }
}