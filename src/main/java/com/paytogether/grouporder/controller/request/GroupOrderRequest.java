package com.paytogether.grouporder.controller.request;

import com.paytogether.grouporder.service.command.CreateGroupOrderCommand;
import java.time.LocalDateTime;

public record GroupOrderRequest(Long productId, int targetQuantity, int price,
                                LocalDateTime startTime,
                                LocalDateTime endTime, boolean sellPolicyAfterDrop) {
  public static CreateGroupOrderCommand toGroupOrderCommand(GroupOrderRequest request) {
    return new CreateGroupOrderCommand(request.productId(), request.targetQuantity(),
        request.price(), request.startTime(), request.endTime(), request.sellPolicyAfterDrop());
  }
}
