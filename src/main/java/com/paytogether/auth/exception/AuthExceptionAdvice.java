package com.paytogether.auth.exception;

import java.time.LocalDateTime;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AuthExceptionAdvice {

  @ExceptionHandler(AuthException.class)
  public ResponseEntity<AuthErrorResponse> advice(AuthException e) {
    AuthErrors errors = e.getErrors();
    AuthErrorResponse authErrorResponse = new AuthErrorResponse(errors.getCode(),
        errors.getExplain(), errors.getStatus().value(), LocalDateTime.now());
    return ResponseEntity.status(errors.getStatus()).body(authErrorResponse);
  }
}
