package com.paytogether.stock.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class StockExceptionAdvice {

  @ExceptionHandler(StockException.class)
  public ResponseEntity<StockErrorResponse> advice(StockException e) {
    StockErrors errors = e.getErrors();
    return ResponseEntity.status(errors.getStatus()).body(StockErrorResponse.from(errors));
  }
}