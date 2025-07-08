package com.paytogether.product.exception;

public class ProductException extends RuntimeException{

  private final ProductErrors errors;

  public ProductException(ProductErrors errors) {
    super(errors.getExplain());
    this.errors = errors;
  }
}
