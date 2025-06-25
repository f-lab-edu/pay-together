package com.paytogether.cart.controller.response;

import com.paytogether.cart.service.CartProductDto;
import java.util.List;

public record CartResponse(List<CartProductDto> cartProductDtoList) {

  public static CartResponse of(List<CartProductDto> cartProductDtoList) {
    return new CartResponse(cartProductDtoList);
  }
}
