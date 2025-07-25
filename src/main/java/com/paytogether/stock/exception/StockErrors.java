package com.paytogether.stock.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum StockErrors {
  NOT_ENOUGH_QUANTITY("S001", HttpStatus.BAD_REQUEST, "재고 수량이 부족합니다"),
  INVALID_PRODUCT_ID("S002", HttpStatus.BAD_REQUEST, "등록된 상품 번호가 없습니다");

  private final String code;
  private final HttpStatus status;
  private final String explain;
}
