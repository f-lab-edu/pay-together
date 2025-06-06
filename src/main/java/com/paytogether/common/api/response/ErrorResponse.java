package com.paytogether.common.api.response;

import lombok.Getter;

@Getter
public class ErrorResponse {

  private final int errorCode;
  private final String errorMessage;

  public ErrorResponse(int errorCode, String errorMessage) {
    this.errorMessage = errorMessage;
    this.errorCode = errorCode;
  }
}
