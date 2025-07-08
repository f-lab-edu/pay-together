package com.paytogether.cart.exception;

public class CartException extends RuntimeException {

  private final CartErrors errors;

  public CartException(CartErrors errors) {
    super(errors.getExplain());
    this.errors = errors;
  }
}
