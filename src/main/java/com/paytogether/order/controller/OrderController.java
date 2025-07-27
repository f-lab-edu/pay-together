package com.paytogether.order.controller;

import com.paytogether.order.controller.request.OrderCreateRequest;
import com.paytogether.order.controller.response.OrderCreateResponse;
import com.paytogether.order.service.OrderService;
import com.paytogether.order.service.result.OrderCreateResult;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/orders")
public class OrderController {

  private final OrderService orderService;

  @PostMapping
  public OrderCreateResponse order(@RequestBody OrderCreateRequest request) {
    OrderCreateResult result = orderService.order(OrderCreateRequest.toOrderCreateCommand(request), LocalDateTime.now());
    return OrderCreateResponse.from(result);
  }
}
