package com.paytogether.common.exception;

import lombok.Getter;

@Getter
public class PayTogetherException extends RuntimeException {

  private final PayTogetherErrors error;

  public PayTogetherException(PayTogetherErrors error) {
    this.error = error;
  }
}
