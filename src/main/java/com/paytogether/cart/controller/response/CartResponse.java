package com.paytogether.cart.controller.response;

import com.paytogether.cart.service.result.CartProductResult;
import java.util.List;

public record CartResponse(List<CartProductResult> cart) {

  public static CartResponse from(List<CartProductResult> cart) {
    return new CartResponse(cart);
  }
}
