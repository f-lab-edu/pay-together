package com.paytogether.cart.controller.request;

import lombok.Getter;

@Getter
public class ProductRequest {
  private Long productId;
  private int quantity;
}
