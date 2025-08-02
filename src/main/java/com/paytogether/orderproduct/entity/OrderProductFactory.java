package com.paytogether.orderproduct.entity;

import com.paytogether.order.service.command.ProductQuantities;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class OrderProductFactory {

  public List<OrderProduct> getOrderProducts(ProductQuantities availableProductQuantities,
      Long orderId) {
    return availableProductQuantities.getProductQuantities().stream()
        .map(pq -> OrderProduct.create(
            orderId,
            pq.productId(),
            pq.quantity(),
            pq.price() * pq.quantity()
        ))
        .collect(Collectors.toList());
  }
}
