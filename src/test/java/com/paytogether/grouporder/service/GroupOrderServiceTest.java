package com.paytogether.grouporder.service;

import com.paytogether.grouporder.repository.GroupOrderRepository;
import com.paytogether.grouporder.service.command.CreateGroupOrderCommand;
import com.paytogether.grouporder.service.result.CreateGroupOrderResult;
import java.time.LocalDateTime;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class GroupOrderServiceTest {

  @Autowired
  private GroupOrderService groupOrderService;

  @Autowired
  private GroupOrderRepository groupOrderRepository;

  @DisplayName("공동구매 상품 생성 성공 테스트")
  @Test
  void createGroupOrder() {
    // given
    LocalDateTime startTime = LocalDateTime.now();
    LocalDateTime endTime = startTime.plusHours(10);
    CreateGroupOrderCommand cgoc = new CreateGroupOrderCommand(1L, 10, 1000,
        startTime, endTime, true);
    // when
    CreateGroupOrderResult result = groupOrderService.createGroupOrder(cgoc);
    // then
    SoftAssertions softAssertions = new SoftAssertions();
    softAssertions.assertThat(result.productId()).isEqualTo(1L);
    softAssertions.assertThat(result.startTime()).isEqualTo(startTime);
    softAssertions.assertThat(result.endTime()).isEqualTo(endTime);
  }
}