package com.paytogether.cart.controller.request;

import lombok.Getter;

@Getter
public class AddProductRequest {
  private Long productId;
  private int quantity;
}
