package com.paytogether.domain.member.entity;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public enum MemberRole {

  MEMBER("회원"),
  ADMIN("관리자");

  private final String explain;
}
