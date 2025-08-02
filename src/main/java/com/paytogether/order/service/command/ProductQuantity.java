package com.paytogether.order.service.command;

public record ProductQuantity(Long productId, int quantity, int price) {

  public static ProductQuantity withoutPrice(Long productId, int quantity) {
    return new ProductQuantity(productId, quantity, 0);
  }
}
