package com.paytogether.member.controller.response;

import lombok.Getter;

@Getter
public class LoginResponse {

  private boolean duplicateLogin;
  private String email;

  private LoginResponse(boolean duplicateLogin, String email) {
    this.duplicateLogin = duplicateLogin;
    this.email = email;
  }

  public static LoginResponse of(String email, boolean duplicateLogin) {
    return new LoginResponse(duplicateLogin, email);
  }
}
