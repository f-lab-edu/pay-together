package com.paytogether.product.entity;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum EventProductStatus {

  WAITING("대기 중"),
  RECRUITING("모집 중"),
  RECRUITMENT_COMPLETED("모집 완료"),
  CANCELED("취소");

  private final String explain;
}
