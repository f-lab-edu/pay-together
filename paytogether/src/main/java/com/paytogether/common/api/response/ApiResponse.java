package com.paytogether.common.api.response;

import lombok.Getter;

@Getter
public class ApiResponse<T> {

  private int status;
  private T body;

  private ApiResponse(int status, T body) {
    this.body = body;
  }

  public static <T> ApiResponse<T> createResponse(int status, T body) {
    return new ApiResponse<>(status, body);
  }
}
