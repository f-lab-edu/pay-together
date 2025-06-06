package com.paytogether.member.entity;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public enum Gender {

  MALE("남"),
  FEMALE("여"),
  OTHER("그외");

  private final String explain;
}
