package com.paytogether.cart.service;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CartProductDto {

  private Long productId;
  private int quantity;

  public CartProductDto(Long productId, int quantity) {
    this.productId = productId;
    this.quantity = quantity;
  }

  public static CartProductDto create(Long productId, int quantity) {
    return new CartProductDto(productId, quantity);
  }
}
