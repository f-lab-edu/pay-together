package com.paytogether.domain.member.service;

import com.paytogether.domain.member.entity.Member;
import com.paytogether.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MemberService {

  private final MemberRepository memberRepository;
  private final PasswordEncoder passwordEncoder;

  @Transactional
  public MemberJoinResponse join(MemberJoinRequest request) {
    String encodedPassword = passwordEncoder.encode(request.getPassword());
    Member member = memberRepository.save(
        Member.createMember(
            request.getEmail(), encodedPassword, request.getName(), request.getAge(),
            request.getGender(), request.getAddress(), request.getPhoneNumber())
    );

    return MemberJoinResponse.from(member);
  }
}
