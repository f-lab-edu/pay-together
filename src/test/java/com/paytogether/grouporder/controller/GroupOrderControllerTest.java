package com.paytogether.grouporder.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paytogether.auth.config.WebConfig;
import com.paytogether.auth.intercepter.LoginCheckInterceptor;
import com.paytogether.config.SecurityConfig;
import com.paytogether.grouporder.controller.request.GroupOrderRequest;
import com.paytogether.grouporder.entity.GroupOrderStatus;
import com.paytogether.grouporder.service.GroupOrderService;
import com.paytogether.grouporder.service.result.CreateGroupOrderResult;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@Import({WebConfig.class, SecurityConfig.class})
@WebMvcTest(GroupOrderController.class)
class GroupOrderControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockitoBean
  private GroupOrderService groupOrderService;


  @MockitoBean
  private LoginCheckInterceptor loginCheckInterceptor;

  @BeforeEach
  void setUp() throws Exception {
    given(loginCheckInterceptor.preHandle(any(), any(), any())).willReturn(true);
  }

  @DisplayName("공동구매 상품 생성 성공 테스트")
  @Test
  void createGroupOrder() throws Exception {
    // given
    Long productId = 1L;
    int targetQuantity = 10;
    LocalDateTime startTime = LocalDateTime.now();
    LocalDateTime endTime = startTime.plusHours(10);
    GroupOrderRequest request = new GroupOrderRequest(1L, 10, 1000, startTime,
        endTime, true);
    CreateGroupOrderResult result = new CreateGroupOrderResult(productId, targetQuantity, 0, 1000,
        startTime, endTime, GroupOrderStatus.RECRUITING, true);
    given(groupOrderService.createGroupOrder(any())).willReturn(result);
    // when
    mockMvc.perform(post("/v1/group/orders")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("productId").value(productId))
        .andExpect(jsonPath("targetQuantity").value(targetQuantity))
        .andExpect(jsonPath("startTime").value(startTime.toString()))
        .andExpect(jsonPath("endTime").value(endTime.toString()));
    // then
  }
}