package com.paytogether.domain.member.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.paytogether.domain.member.entity.Gender;
import com.paytogether.domain.member.entity.Member;
import com.paytogether.domain.member.entity.MemberRole;
import com.paytogether.domain.member.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SessionMVCTest {

  @Autowired
  private TestRestTemplate restTemplate;
  @Autowired
  private MemberRepository memberRepository;
  @Autowired
  private PasswordEncoder passwordEncoder;

  @BeforeEach
  void setUp() {
    memberRepository.save(createMember("pay1@spring.com", "1234567!"));
    memberRepository.save(createMember("pay2@spring.com", "1234567!"));
  }

  @Test
  @DisplayName("서로 다른 사용자 로그인 시 독립된 세션이 생성되어야 한다")
  void login_different_member_different_session() {
    MemberLoginRequest user1Request = new MemberLoginRequest("pay1@spring.com", "1234567!");
    MemberLoginRequest user2Request = new MemberLoginRequest("pay2@spring.com", "1234567!");

    // 첫 번째 사용자 로그인
    ResponseEntity<String> response1 = restTemplate.postForEntity("/member/login", user1Request,
        String.class);
    String sessionId1 = response1.getHeaders().getFirst("Set-Cookie");

    // 두 번째 사용자 로그인
    ResponseEntity<String> response2 = restTemplate.postForEntity("/member/login", user2Request,
        String.class);
    String sessionId2 = response2.getHeaders().getFirst("Set-Cookie");

    assertThat(sessionId1).isNotNull();
    assertThat(sessionId2).isNotNull();
    assertThat(sessionId1).isNotEqualTo(sessionId2);
  }

  private Member createMember(String email, String password) {
    MemberJoinRequest request = MemberJoinRequest.builder()
        .age(100)
        .name("pay")
        .gender(Gender.MALE)
        .address("seoul")
        .password(password)
        .email(email)
        .phoneNumber("01012345678")
        .build();
    String encoded = passwordEncoder.encode(password);
    return createMember(request, encoded);
  }

  private Member createMember(MemberJoinRequest request, String encodedPassword) {
    return Member.builder()
        .email(request.getEmail()).password(encodedPassword).name(request.getName())
        .age(request.getAge()).gender(request.getGender()).address(request.getAddress())
        .phoneNumber(request.getPhoneNumber()).role(MemberRole.MEMBER)
        .build();
  }
}
