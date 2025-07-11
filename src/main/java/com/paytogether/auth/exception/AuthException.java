package com.paytogether.auth.exception;

import lombok.Getter;

@Getter
public class AuthException extends RuntimeException {

  private final AuthErrors errors;

  public AuthException(AuthErrors errors) {
    super(errors.getExplain());
    this.errors = errors;
  }
}
