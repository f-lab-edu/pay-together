package com.paytogether.orderproduct.entity;

import com.paytogether.order.service.command.ProductQuantities;
import com.paytogether.order.service.command.ProductQuantity;
import com.paytogether.product.entity.Product;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class OrderProductFactory {

  public List<OrderProduct> getOrderProducts(ProductQuantities availableProductQuantities,
      List<Product> products,
      Long orderId
  ) {
    Map<Long, Product> productMap = products.stream()
        .collect(Collectors.toMap(
            product -> product.getId(),
            product -> product
        ));

    return availableProductQuantities.getProductQuantities().stream()
        .map(pq -> toOrderProduct(pq, productMap, orderId))
        .collect(Collectors.toList());
  }

  private OrderProduct toOrderProduct(
      ProductQuantity pq,
      Map<Long, Product> productMap,
      Long orderId
  ) {
    Product product = productMap.get(pq.productId());
    return OrderProduct.create(orderId, pq.productId(), pq.quantity(),
        product.getPrice() * pq.quantity());
  }
}
