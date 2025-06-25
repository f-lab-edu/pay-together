package com.paytogether.cart.service.result;

import com.paytogether.cart.service.CartProductDto;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.Getter;

@Getter
public class CartResult {

  private List<CartProductDto> cartProductDtoList;

  private CartResult(List<CartProductDto> cartProductDtoList) {
    this.cartProductDtoList = cartProductDtoList;
  }

  public static CartResult of(Map<Long, CartProductDto> cartProductDtoMap) {
    return new CartResult(convertCartMapToList(cartProductDtoMap));
  }

  public static List<CartProductDto> convertCartMapToList(Map<Long, CartProductDto> cartProductDtoMap) {
    return new ArrayList<>(cartProductDtoMap.values());
  }
}
