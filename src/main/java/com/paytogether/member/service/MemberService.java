package com.paytogether.member.service;

import com.paytogether.member.entity.Member;
import com.paytogether.member.repository.MemberRepository;
import com.paytogether.member.service.command.MemberJoinCommand;
import com.paytogether.member.service.result.MemberJoinResult;
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
  public MemberJoinResult join(MemberJoinCommand command, LocalDateTime now) {
    String encodedPassword = passwordEncoder.encode(command.getPassword());
    Member member = memberRepository.save(
        Member.createMember(command.getEmail(), encodedPassword, command.getName(),
            command.getAge(), command.getGender(), command.getAddress(), command.getPhoneNumber(),
            now));
    return MemberJoinResult.fromMember(member);
  }
}
