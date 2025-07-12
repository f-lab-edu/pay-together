package com.paytogether.exception;

import java.time.LocalDateTime;

public record ErrorResponse(String explain, int status, LocalDateTime now, String path) {

  public static ErrorResponse of(String explain, int status, String path) {
    return new ErrorResponse(explain, status, LocalDateTime.now(), path);
  }
}
