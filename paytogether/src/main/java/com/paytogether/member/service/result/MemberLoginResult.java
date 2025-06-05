package com.paytogether.member.service.result;

import com.paytogether.member.entity.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberLoginResult {

  private String email;

  public static MemberLoginResult fromMember(Member member) {
    return MemberLoginResult.builder()
        .email(member.getEmail())
        .build();
  }
}
