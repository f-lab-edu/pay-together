package com.paytogether.member.controller.response;

import com.paytogether.member.entity.Member;
import com.paytogether.member.service.result.MemberJoinResult;

public record MemberJoinResponse(
    String email
) {

  public static MemberJoinResponse from(Member member) {
    return new MemberJoinResponse(member.getEmail());
  }

  public static MemberJoinResponse fromMemberLoginResult(MemberJoinResult result) {
    return new MemberJoinResponse(result.email());
  }
}
