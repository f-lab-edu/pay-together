package com.paytogether.order.service.result;

import com.paytogether.orderproduct.entity.OrderProduct;
import java.util.List;
import java.util.stream.Collectors;

public record OrderProductResult(Long productId, int quantity, int totalPrice) {

  public static OrderProductResult from(OrderProduct orderProduct) {
    return new OrderProductResult(
        orderProduct.getProductId(),
        orderProduct.getQuantity(),
        orderProduct.getTotalPrice()
    );
  }

  public static List<OrderProductResult> from(List<OrderProduct> orderProducts) {
    return orderProducts.stream()
        .map(OrderProductResult::from)
        .collect(Collectors.toList());
  }
}
