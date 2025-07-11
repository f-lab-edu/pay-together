package com.paytogether.auth.controller.response;

public record LoginResponse(
    boolean duplicateLogin,
    String email
) {

  public static LoginResponse of(String email, boolean duplicateLogin) {
    return new LoginResponse(duplicateLogin, email);
  }
}
