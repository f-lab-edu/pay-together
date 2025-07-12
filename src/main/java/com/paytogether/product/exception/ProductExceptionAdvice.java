package com.paytogether.product.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ProductExceptionAdvice {

  @ExceptionHandler(ProductException.class)
  public ResponseEntity<ProductErrorResponse> advice(ProductException e) {
    ProductErrors errors = e.getErrors();
    ProductErrorResponse productErrorResponse = ProductErrorResponse.from(errors);
    return ResponseEntity.status(errors.getStatus()).body(productErrorResponse);
  }
}
