package com.paytogether.member.service;

import com.paytogether.member.entity.Member;
import com.paytogether.member.repository.MemberRepository;
import java.time.LocalDateTime;
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
  public MemberJoinResponse join(MemberJoinRequest request, LocalDateTime now) {
    String encodedPassword = passwordEncoder.encode(request.getPassword());
    Member member = memberRepository.save(
        Member.createMember(
            request.getEmail(), encodedPassword, request.getName(), request.getAge(),
            request.getGender(), request.getAddress(), request.getPhoneNumber(), now)
    );

    return MemberJoinResponse.from(member);
  }
}
