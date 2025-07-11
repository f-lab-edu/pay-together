package com.paytogether.cart.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CartErrors {

  INVALID_INFO("C001", HttpStatus.BAD_REQUEST, "잘못된 정보를 입력했습니다");

  private final String code;
  private final HttpStatus status;
  private final String explain;
}
