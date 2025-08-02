package com.paytogether.order.service;

import com.paytogether.auth.service.HttpSessionManager;
import com.paytogether.order.entity.Order;
import com.paytogether.order.repository.OrderRepository;
import com.paytogether.order.service.command.ProductQuantities;
import com.paytogether.order.service.result.OrderCreateResult;
import com.paytogether.order.service.result.OrderProductResult;
import com.paytogether.orderproduct.entity.OrderProduct;
import com.paytogether.orderproduct.service.OrderProductService;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OrderService {

  private final HttpSessionManager httpSessionManager;
  private final OrderRepository orderRepository;
  private final OrderProductService orderProductService;

  public OrderCreateResult order(ProductQuantities productQuantities, LocalDateTime orderedAt) {
    int totalPrice = productQuantities.calTotalPrice();
    Long memberId = httpSessionManager.getMemberId();

    Order order = Order.create(memberId, totalPrice, orderedAt);
    Order savedOrder = orderRepository.save(order);
    Long orderId = savedOrder.getId();

    List<OrderProduct> orderProducts = orderProductService.createOrderProduct(productQuantities,
        orderId);

    List<OrderProductResult> result = OrderProductResult.from(orderProducts);
    return OrderCreateResult.of(totalPrice, result);
  }
}
