package com.paytogether.cart.controller.response;

import com.paytogether.cart.service.CartDto;
import java.util.List;

public record CartResponse(List<CartDto> cartDtoList) {

  public static CartResponse of(List<CartDto> cart) {
    return new CartResponse(cart);
  }
}
