package com.paytogether.cart.exception;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CartException extends RuntimeException{

  private final CartErrors errors;
}
