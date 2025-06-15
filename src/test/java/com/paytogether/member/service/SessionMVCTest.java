package com.paytogether.member.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.paytogether.member.controller.request.MemberLoginRequest;
import com.paytogether.member.entity.Gender;
import com.paytogether.member.entity.Member;
import com.paytogether.member.repository.MemberRepository;
import java.time.LocalDateTime;
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
    Member member1 = Member.createMember("pay1@spring.com", passwordEncoder.encode("1234567!"),
        "name", 100, Gender.MALE,
        "address", "01012345678", LocalDateTime.now());
    memberRepository.save(member1);

    Member member2 = Member.createMember("pay2@spring.com", passwordEncoder.encode("1234567!"),
        "name", 100, Gender.MALE,
        "address", "01012345678", LocalDateTime.now());
    memberRepository.save(member2);
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
}
