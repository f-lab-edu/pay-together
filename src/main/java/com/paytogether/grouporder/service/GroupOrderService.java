package com.paytogether.grouporder.service;

import com.paytogether.grouporder.entity.GroupOrder;
import com.paytogether.grouporder.repository.GroupOrderRepository;
import com.paytogether.grouporder.service.command.CreateGroupOrderCommand;
import com.paytogether.grouporder.service.result.CreateGroupOrderResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class GroupOrderService {

  private final GroupOrderRepository groupOrderRepository;

  @Transactional
  public CreateGroupOrderResult createGroupOrder(CreateGroupOrderCommand command) {
    GroupOrder groupOrder = groupOrderRepository.save(
        GroupOrder.create(command.productId(), command.targetQuantity(),
            command.price(), command.startTime(),
            command.endTime(), command.sellPolicyAfterDrop()));
    return CreateGroupOrderResult.from(groupOrder);
  }
}
