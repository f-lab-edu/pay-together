package com.paytogether.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PayTogetherErrors {
  MEMBER_NOT_FOUND("등록된 회원이 없습니다"),
  INVALID_INFO("잘못된 정보를 입력했습니다");

  private final String errorMessage;
}
