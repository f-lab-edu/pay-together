package com.paytogether.stock.exception;

import java.time.LocalDateTime;

public record StockErrorResponse(String code, String explain, int status, LocalDateTime now) {

  public static StockErrorResponse from(StockErrors errors) {
    return new StockErrorResponse(errors.getCode(), errors.getExplain(), errors.getStatus().value(),
        LocalDateTime.now());
  }
}
