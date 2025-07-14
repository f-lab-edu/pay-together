package com.paytogether.auth.exception;

import java.time.LocalDateTime;

public record AuthErrorResponse(String code, String explain, int status, LocalDateTime now) {

  public static AuthErrorResponse from(AuthErrors errors) {
    return new AuthErrorResponse(errors.getCode(), errors.getExplain(), errors.getStatus().value(),
        LocalDateTime.now());
  }
}
