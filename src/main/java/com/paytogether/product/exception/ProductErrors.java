package com.paytogether.product.exception;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ProductErrors {

  PRODUCT_NOT_FOUND("상품을 찾을 수 없습니다");

  private final String explain;
}
