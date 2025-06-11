package com.paytogether.member.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.paytogether.member.controller.request.MemberJoinRequest;
import com.paytogether.member.entity.Gender;
import com.paytogether.member.entity.Member;
import com.paytogether.member.entity.MemberRole;
import java.time.LocalDateTime;
import java.util.Optional;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class MemberRepositoryTest {

  @Autowired
  private MemberRepository memberRepository;

  @Test
  void findMemberByEmail() {
    //given
    Member member = Member.createMember("pay@spring.com", "123456!7", "name", 100, Gender.MALE,
        "address", "01012345678", LocalDateTime.now());
    memberRepository.save(member);

    //when
    Optional<Member> result = memberRepository.findByEmail("pay@spring.com");
    Member savedMember = result.get();

    //then
    assertThat(savedMember).isEqualTo(member);
  }

  @Test
  void saveTest() {
    // given
    LocalDateTime now = LocalDateTime.now();
    Member member = Member.createMember("pay@spring.com", "1234567!", "pay", 100,
        Gender.MALE, "address", "01012345678", now);

    // when
    Member savedMember = memberRepository.save(member);

    // then
    SoftAssertions softAssertions = new SoftAssertions();
    softAssertions.assertThat(savedMember.getCreatedAt()).isEqualTo(now);
    softAssertions.assertThat(savedMember).isEqualTo(member);
    softAssertions.assertAll();
  }

  private Member createMember(MemberJoinRequest request, String encodedPassword) {
    return Member.builder()
        .email(request.getEmail()).password(encodedPassword).name(request.getName())
        .age(request.getAge()).gender(request.getGender()).address(request.getAddress())
        .phoneNumber(request.getPhoneNumber()).role(MemberRole.MEMBER)
        .build();
  }
}