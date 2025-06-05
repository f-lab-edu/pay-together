package com.paytogether.member.service;

import com.paytogether.member.api.request.MemberJoinRequest;
import com.paytogether.member.api.response.MemberJoinResponse;
import com.paytogether.member.entity.Member;
import com.paytogether.member.repository.MemberRepository;
import com.paytogether.member.service.command.MemberLoginCommand;
import com.paytogether.member.service.result.MemberLoginResult;
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

  @Transactional
  public MemberLoginResult join(MemberLoginCommand command, LocalDateTime now) {
    String encodedPassword = passwordEncoder.encode(command.getPassword());
    Member member = memberRepository.save(
        Member.createMember(
            command.getEmail(), encodedPassword, command.getName(), command.getAge(),
            command.getGender(), command.getAddress(), command.getPhoneNumber(), now)
    );
    return MemberLoginResult.fromMember(member);
  }
}
