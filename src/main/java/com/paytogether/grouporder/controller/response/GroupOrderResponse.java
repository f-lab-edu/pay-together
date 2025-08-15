package com.paytogether.grouporder.controller.response;

import com.paytogether.grouporder.entity.GroupOrderStatus;
import com.paytogether.grouporder.service.result.CreateGroupOrderResult;
import java.time.LocalDateTime;

public record GroupOrderResponse(Long productId, int targetQuantity, int currentQuantity,
                                 int price,
                                 LocalDateTime startTime, LocalDateTime endTime,
                                 GroupOrderStatus status, boolean sellPolicyAfterDrop) {

  public static GroupOrderResponse from(CreateGroupOrderResult result) {
    return new GroupOrderResponse(result.productId(), result.targetQuantity(),
        result.currentQuantity(), result.price(), result.startTime(), result.endTime(),
        result.status(), result.sellPolicyAfterDrop());
  }
}
