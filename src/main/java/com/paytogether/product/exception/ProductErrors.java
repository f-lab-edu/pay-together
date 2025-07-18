package com.paytogether.product.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ProductErrors {

  PRODUCT_NOT_FOUND("P001", HttpStatus.BAD_REQUEST, "상품을 찾을 수 없습니다"),
  NOT_ENOUGH_QUANTITY("P002", HttpStatus.BAD_REQUEST, "수량이 부족합니다");

  private final String code;
  private final HttpStatus status;
  private final String explain;
}
