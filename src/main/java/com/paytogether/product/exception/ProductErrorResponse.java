package com.paytogether.product.exception;

import java.time.LocalDateTime;

public record ProductErrorResponse(String code, String explain, int status, LocalDateTime now) {

  public static ProductErrorResponse from(ProductErrors errors) {
    return new ProductErrorResponse(errors.getCode(), errors.getExplain(),
        errors.getStatus().value(), LocalDateTime.now());
  }
}
