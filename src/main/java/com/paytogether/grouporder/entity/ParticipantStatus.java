package com.paytogether.grouporder.entity;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ParticipantStatus {

  JOINED("참여 중"),
  PAYMENT_COMPLETED("결제 완료"),
  CANCELED("참여 취소");

  private final String explain;
}
