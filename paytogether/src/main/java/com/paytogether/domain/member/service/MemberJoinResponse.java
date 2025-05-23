package com.paytogether.domain.member.service;

import com.paytogether.domain.member.entity.Member;
import lombok.Getter;

@Getter
public class MemberJoinResponse {

  private String email;

  private MemberJoinResponse(String email) {
    this.email = email;
  }

  public static MemberJoinResponse from(Member member) {
    return new MemberJoinResponse(member.getEmail());
  }
}
