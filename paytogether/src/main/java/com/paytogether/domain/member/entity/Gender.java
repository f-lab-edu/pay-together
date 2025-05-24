package com.paytogether.domain.member.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Gender {

  MALE("남"),
  FEMALE("여"),
  OTHER("그외");

  private final String text;
}
