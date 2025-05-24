package com.paytogether.domain.member.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.paytogether.domain.member.entity.Gender;
import com.paytogether.domain.member.entity.Member;
import com.paytogether.domain.member.service.MemberJoinRequest;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class MemberRepositoryTest {

  @Autowired
  private MemberRepository memberRepository;

  @Test
  void findMemberByEmail() {
    MemberJoinRequest request = MemberJoinRequest.builder()
        .age(100)
        .name("pay")
        .gender(Gender.MALE)
        .address("seoul")
        .password("1234!678")
        .email("pay@spring.com")
        .phoneNumber("01012345678")
        .build();
    Member m = Member.createMember(request, "encodedPassword");
    memberRepository.save(m);

    Optional<Member> result = memberRepository.findByEmail("pay@spring.com");

    assertThat(result).isNotNull();
  }
}