package com.paytogether.grouporder.service.result;

import com.paytogether.grouporder.entity.GroupOrder;
import com.paytogether.grouporder.entity.GroupOrderStatus;
import java.time.LocalDateTime;

public record CreateGroupOrderResult(Long productId, int targetQuantity, int currentQuantity,
                                     int price,
                                     LocalDateTime startTime, LocalDateTime endTime,
                                     GroupOrderStatus status, boolean sellPolicyAfterDrop) {

  public static CreateGroupOrderResult from(GroupOrder groupOrder) {
    return new CreateGroupOrderResult(groupOrder.getProductId(), groupOrder.getTargetQuantity(),
        groupOrder.getCurrentQuantity(), groupOrder.getPrice(), groupOrder.getStartTime(),
        groupOrder.getEndTime(),
        groupOrder.getStatus(), groupOrder.isSellPolicyAfterDrop());
  }
}
