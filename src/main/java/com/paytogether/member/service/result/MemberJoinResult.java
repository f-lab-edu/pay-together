package com.paytogether.member.service.result;

import com.paytogether.member.entity.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberJoinResult {

  private String email;

  public static MemberJoinResult fromMember(Member member) {
    return MemberJoinResult.builder()
        .email(member.getEmail())
        .build();
  }
}
