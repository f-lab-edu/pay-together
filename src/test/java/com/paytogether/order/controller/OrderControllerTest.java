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
import com.paytogether.cart.controller.request.AddCartProductRequest;
import com.paytogether.cart.service.result.CartProductResult;
import com.paytogether.config.SecurityConfig;
import com.paytogether.order.controller.request.OrderCreateRequest;
import com.paytogether.order.service.OrderService;
import com.paytogether.order.service.result.OrderCreateResult;
import com.paytogether.order.service.result.OrderProductResult;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
  private OrderService orderService;

  @MockitoBean
  private LoginCheckInterceptor loginCheckInterceptor;

  @BeforeEach
  void setUp() throws Exception {
    given(loginCheckInterceptor.preHandle(any(), any(), any())).willReturn(true);
  }

  @DisplayName("주문 성공 테스")
  @Test
  void order() throws Exception {
    // given
    Map<Long, Integer> productQuantityMap = new HashMap<>();
    Long productId1 = 1L;
    int productQuantity1 = 10;
    productQuantityMap.put(productId1, productQuantity1);

    Long productId2 = 2L;
    int productQuantity2 = 20;
    productQuantityMap.put(productId2, productQuantity2);
    OrderCreateRequest request = new OrderCreateRequest(productQuantityMap);

    OrderCreateResult orderCreateResult = new OrderCreateResult(
        900,
        List.of(
            new OrderProductResult(productId1, productQuantity1, 100),
            new OrderProductResult(productId2, productQuantity2, 800)
        )
    );
    given(orderService.order(any(), any())).willReturn(orderCreateResult);

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
