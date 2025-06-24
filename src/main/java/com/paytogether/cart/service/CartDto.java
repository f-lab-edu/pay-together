package com.paytogether.cart.service;

import java.io.Serializable;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CartDto {

  private Long productId;
  private int quantity;

  public CartDto(Long productId, int quantity) {
    this.productId = productId;
    this.quantity = quantity;
  }

  public static CartDto create(Long productId, int quantity) {
    return new CartDto(productId, quantity);
  }

  public int adjust(int quantity) {
    return this.quantity = quantity;
  }
}
