package com.paytogether.grouporder.controller;

import com.paytogether.grouporder.controller.request.GroupOrderRequest;
import com.paytogether.grouporder.controller.response.GroupOrderResponse;
import com.paytogether.grouporder.service.GroupOrderService;
import com.paytogether.grouporder.service.result.CreateGroupOrderResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/v1/group/orders")
@RestController
public class GroupOrderController {

  private final GroupOrderService groupOrderService;

  @PostMapping
  public GroupOrderResponse create(@RequestBody GroupOrderRequest request) {
    CreateGroupOrderResult result = groupOrderService.createGroupOrder(
        GroupOrderRequest.toGroupOrderCommand(request));
    return GroupOrderResponse.from(result);
  }
}
