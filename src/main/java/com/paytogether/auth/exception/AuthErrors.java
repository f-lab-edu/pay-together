package com.paytogether.auth.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AuthErrors {

  LOGIN_REQUIRED("로그인 하지 않은 사용자입니다");

  private final String explain;
}
