package com.paytogether.cart.service.result;

import com.paytogether.cart.service.CartDto;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.Getter;

@Getter
public class CartResult {

  private List<CartDto> cart;

  private CartResult(List<CartDto> cart) {
    this.cart = cart;
  }

  public static CartResult of(Map<Long, CartDto> cart) {
    return new CartResult(convertCartMapToList(cart));
  }

  public static List<CartDto> convertCartMapToList(Map<Long, CartDto> cartMap) {
    return new ArrayList<>(cartMap.values());
  }
}
