package com.paytogether.cart.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CartErrors {

  INVALID_INFO("잘못된 정보를 입력했습니다");

  private final String explain;
}
