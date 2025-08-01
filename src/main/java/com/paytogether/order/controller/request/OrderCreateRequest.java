package com.paytogether.order.controller.request;

import com.paytogether.order.service.command.OrderCreateCommand;
import com.paytogether.order.service.command.ProductQuantity;
import java.util.List;
import java.util.stream.Collectors;

public record OrderCreateRequest(Long productId, int quantity) {

  public static OrderCreateCommand toOrderCreateCommand(List<OrderCreateRequest> request) {
    List<ProductQuantity> productQuantityList = request.stream()
        .map(orderCreateRequest -> new ProductQuantity(
            orderCreateRequest.productId(),
            orderCreateRequest.quantity()
        ))
        .collect(Collectors.toList());

    return new OrderCreateCommand(productQuantityList);
  }
}
