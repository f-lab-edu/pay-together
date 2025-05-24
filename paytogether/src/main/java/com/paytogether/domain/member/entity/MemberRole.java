package com.paytogether.domain.member.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MemberRole {

  MEMBER("회원"),
  ADMIN("관리자");

  private final String text;
}
