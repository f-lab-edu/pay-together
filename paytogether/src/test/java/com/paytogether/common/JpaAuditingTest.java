package com.paytogether.common;

import com.paytogether.member.entity.Gender;
import com.paytogether.member.entity.Member;
import com.paytogether.member.repository.MemberRepository;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JpaAuditingTest {

  @Autowired
  MemberRepository memberRepository;

  @Test
  void auditingTest() {
    //given
    Member member = Member.createMember("email@spring.com", "123456!6", "pay", 100, Gender.MALE,
        "asdad", "01040121234");

    //when
    memberRepository.save(member);

    //then
    SoftAssertions softAssertions = new SoftAssertions();
    softAssertions.assertThat(member.getCreatedAt()).isNotNull();
    softAssertions.assertThat(member.getModifiedAt()).isNotNull();
    softAssertions.assertAll();
  }
}