package com.paytogether.order.controller;

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
import com.paytogether.order.controller.request.OrderCreateRequest;
import com.paytogether.order.service.OrderFacade;
import com.paytogether.order.service.result.OrderCreateResult;
import com.paytogether.order.service.result.OrderProductResult;
import java.util.List;
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
@WebMvcTest(OrderController.class)
class OrderControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockitoBean
  private OrderFacade orderFacade;

  @MockitoBean
  private LoginCheckInterceptor loginCheckInterceptor;

  @BeforeEach
  void setUp() throws Exception {
    given(loginCheckInterceptor.preHandle(any(), any(), any())).willReturn(true);
  }

  @DisplayName("주문 성공 테스트")
  @Test
  void order() throws Exception {
    // given
    Long productId1 = 1L;
    int productQuantity1 = 10;
    OrderCreateRequest ocr1 = new OrderCreateRequest(productId1, productQuantity1);

    Long productId2 = 2L;
    int productQuantity2 = 20;
    OrderCreateRequest ocr2 = new OrderCreateRequest(productId1, productQuantity1);
    List<OrderCreateRequest> request = List.of(ocr1, ocr2);

    OrderCreateResult orderCreateResult = new OrderCreateResult(
        900,
        List.of(
            new OrderProductResult(productId1, productQuantity1, 100),
            new OrderProductResult(productId2, productQuantity2, 800)
        )
    );
    given(orderFacade.order(any(), any())).willReturn(orderCreateResult);

    // when
    mockMvc.perform(post("/v1/orders")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("orderTotalPrice").value(900))
        .andExpect(jsonPath("orderProductResults[0].productId").value(productId1))
        .andExpect(jsonPath("orderProductResults[0].quantity").value(productQuantity1))
        .andExpect(jsonPath("orderProductResults[1].productId").value(productId2))
        .andExpect(jsonPath("orderProductResults[1].quantity").value(productQuantity2));
  }
}
