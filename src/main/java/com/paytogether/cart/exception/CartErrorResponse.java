package com.paytogether.cart.exception;

import java.time.LocalDateTime;

public record CartErrorResponse(String code, String explain, int status, LocalDateTime now) {

  public static CartErrorResponse from(CartErrors errors) {
    return new CartErrorResponse(errors.getCode(), errors.getExplain(), errors.getStatus().value(),
        LocalDateTime.now());
  }
}
