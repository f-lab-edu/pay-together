package com.paytogether.member.service;

import com.paytogether.member.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberJoinResponse {

  private String email;

  private MemberJoinResponse(String email) {
    this.email = email;
  }

  public static MemberJoinResponse from(Member member) {
    return new MemberJoinResponse(member.getEmail());
  }
}
