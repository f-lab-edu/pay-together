package com.paytogether.cart.service.result;

import java.util.HashMap;
import java.util.Map;
import lombok.Getter;

@Getter
public class CartResult {

  private Map<String, String> cart = new HashMap<>();

  private CartResult(Map<String, String> cart) {
    this.cart = cart;
  }

  public static CartResult of(Map<String, String> cart) {
    return new CartResult(cart);
  }
}
