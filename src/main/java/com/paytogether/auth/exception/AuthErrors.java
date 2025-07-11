package com.paytogether.auth.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum AuthErrors {

  LOGIN_REQUIRED("A001", HttpStatus.FORBIDDEN, "로그인 하지 않은 사용자입니다");

  private final String code;
  private final HttpStatus status;
  private final String explain;
}
