package com.paytogether.order.controller.request;

import com.paytogether.order.service.command.OrderCreateCommand;
import java.util.Map;

public record OrderCreateRequest(Map<Long, Integer> productQuantityMap) {

  public static OrderCreateCommand toOrderCreateCommand(OrderCreateRequest orderCreateRequest) {
    return new OrderCreateCommand(orderCreateRequest.productQuantityMap);
  }
}
