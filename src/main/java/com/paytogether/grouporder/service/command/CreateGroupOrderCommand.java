package com.paytogether.grouporder.service.command;

import java.time.LocalDateTime;

public record CreateGroupOrderCommand(Long productId, int targetQuantity, int price,
                                      LocalDateTime startTime,
                                      LocalDateTime endTime, boolean sellPolicyAfterDrop) {
}
