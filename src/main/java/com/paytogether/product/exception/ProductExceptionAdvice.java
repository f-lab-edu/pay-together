package com.paytogether.product.exception;

import java.time.LocalDateTime;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ProductExceptionAdvice {

  @ExceptionHandler(ProductException.class)
  public ResponseEntity<ProductErrorResponse> advice(ProductException e) {
    ProductErrors errors = e.getErrors();
    ProductErrorResponse productErrorResponse = new ProductErrorResponse(errors.getCode(),
        errors.getExplain(), errors.getStatus().value(), LocalDateTime.now());
    return ResponseEntity.status(errors.getStatus()).body(productErrorResponse);
  }
}
