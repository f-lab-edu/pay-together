package com.paytogether.order.service.result;

import java.util.List;

public record OrderCreateResult(int orderTotalPrice, List<OrderProductResult> orderProductResults) {

  public static OrderCreateResult of(int orderTotalPrice, List<OrderProductResult> orderProductResults) {
    return new OrderCreateResult(orderTotalPrice, orderProductResults);
  }
}
