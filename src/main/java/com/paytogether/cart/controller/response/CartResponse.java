package com.paytogether.cart.controller.response;

import com.paytogether.cart.service.CartDto;
import java.util.List;
import lombok.Getter;

@Getter
public class CartResponse {

  private List<CartDto> cart;

  private CartResponse(List<CartDto> cart) {
    this.cart = cart;
  }

  public static CartResponse of(List<CartDto> cart) {
    return new CartResponse(cart);
  }
}
