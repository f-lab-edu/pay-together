package com.paytogether.order.controller.response;

import com.paytogether.order.service.result.OrderCreateResult;
import com.paytogether.order.service.result.OrderProductResult;
import java.util.List;

public record OrderCreateResponse(int orderTotalPrice, List<OrderProductResult> orderProductResults) {

  public static OrderCreateResponse from(OrderCreateResult orderCreateResult) {
    return new OrderCreateResponse(orderCreateResult.orderTotalPrice(), orderCreateResult.orderProductResults());
  }
}
