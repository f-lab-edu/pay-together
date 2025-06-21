package com.paytogether.cart.controller.response;

import java.util.HashMap;
import java.util.Map;
import lombok.Getter;

@Getter
public class CartResponse {

  private Map<String, String> cart;

  private CartResponse(Map<String, String> cart) {
    this.cart = cart;
  }

  public static CartResponse of(Map<String, String> cart) {
    return new CartResponse(cart);
  }
}
