package com.paytogether.cart.exception;

import java.time.LocalDateTime;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CartExceptionAdvice {

  @ExceptionHandler(CartException.class)
  public ResponseEntity<CartErrorResponse> advice(CartException e) {
    CartErrors errors = e.getErrors();
    CartErrorResponse cartErrorResponse = new CartErrorResponse(errors.getCode(),
        errors.getExplain(), errors.getStatus().value(), LocalDateTime.now());
    return ResponseEntity.status(errors.getStatus()).body(cartErrorResponse);
  }
}
