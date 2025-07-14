package com.paytogether.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionAdvice {

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> advice(Exception e, HttpServletRequest request) {
    log.error("예외 발생 : {}", e);
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(ErrorResponse.of(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), request.getRequestURI()));
  }
}
