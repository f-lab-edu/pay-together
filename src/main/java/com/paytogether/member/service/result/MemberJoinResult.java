package com.paytogether.member.service.result;

import com.paytogether.member.entity.Member;
import lombok.Builder;

@Builder
public record MemberJoinResult(String email) {

  public static MemberJoinResult fromMember(Member member) {
    return MemberJoinResult.builder()
        .email(member.getEmail())
        .build();
  }
}
