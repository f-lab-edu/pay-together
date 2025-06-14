package com.paytogether.member.controller.response;

import com.paytogether.member.entity.Member;
import com.paytogether.member.service.result.MemberJoinResult;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
public class MemberJoinResponse {

  private String email;

  private MemberJoinResponse(String email) {
    this.email = email;
  }

  public static MemberJoinResponse from(Member member) {
    return new MemberJoinResponse(member.getEmail());
  }

  public static MemberJoinResponse fromMemberLoginResult(MemberJoinResult result) {
    return MemberJoinResponse.builder()
        .email(result.getEmail())
        .build();
  }
}
