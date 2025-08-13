package com.paytogether.grouporder.entity;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum GroupOrderStatus {

  RECRUITING("모집 중"),
  RECRUITMENT_COMPLETED("모집 완료"),
  CANCELED("취소");

  private final String explain;
}
