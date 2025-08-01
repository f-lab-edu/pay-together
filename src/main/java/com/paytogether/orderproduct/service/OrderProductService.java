package com.paytogether.orderproduct.service;

import com.paytogether.order.service.command.ProductQuantities;
import com.paytogether.orderproduct.entity.OrderProduct;
import com.paytogether.orderproduct.entity.OrderProductFactory;
import com.paytogether.orderproduct.repository.OrderProductRepository;
import com.paytogether.product.entity.Product;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OrderProductService {

  private final OrderProductRepository orderProductRepository;
  private final OrderProductFactory orderProductFactory;

  public List<OrderProduct> createOrderProduct(ProductQuantities productQuantities,
      List<Product> products, Long orderId) {
    List<OrderProduct> orderProducts = orderProductFactory.getOrderProducts(productQuantities,
        products, orderId);
    orderProductRepository.saveAll(orderProducts);
    return orderProducts;
  }
}
