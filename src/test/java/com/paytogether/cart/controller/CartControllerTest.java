package com.paytogether.cart.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paytogether.auth.config.WebConfig;
import com.paytogether.auth.intercepter.LoginCheckInterceptor;
import com.paytogether.cart.controller.request.AddCartProductRequest;
import com.paytogether.cart.service.CartService;
import com.paytogether.cart.service.result.CartProductResult;
import com.paytogether.config.SecurityConfig;
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
@WebMvcTest(CartController.class)
class CartControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private CartService cartService;

  @Autowired
  private ObjectMapper objectMapper;

  @MockitoBean
  private LoginCheckInterceptor loginCheckInterceptor;

  @BeforeEach
  void setUp() throws Exception {
    given(loginCheckInterceptor.preHandle(any(), any(), any())).willReturn(true);
  }

  @DisplayName("상품을 장바구니에 추가 요청 성공 테스트")
  @Test
  void saveProduct() throws Exception {
    // given
    Long productId = 1L;
    int quantity = 10;
    CartProductResult cartProductResult1 = new CartProductResult(productId, quantity);

    given(cartService.saveProduct(productId, quantity)).willReturn(List.of(cartProductResult1));

    AddCartProductRequest request = new AddCartProductRequest(productId, quantity);

    // when
    mockMvc.perform(post("/carts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("cart[0].productId").value(productId))
        .andExpect(jsonPath("cart[0].quantity").value(quantity));
  }

  @DisplayName("장바구니 조회 성공 테스트")
  @Test
  void getCart() throws Exception {
    // given
    Long productId1 = 1L;
    int quantity1 = 10;
    CartProductResult cartProductResult1 = new CartProductResult(productId1, quantity1);

    Long productId2 = 2L;
    int quantity2 = 20;
    CartProductResult cartProductResult2 = new CartProductResult(productId2, quantity2);

    given(cartService.getCart()).willReturn(List.of(cartProductResult1, cartProductResult2));

    // when
    mockMvc.perform(get("/carts")
            .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("cart[0].productId").value(productId1))
        .andExpect(jsonPath("cart[0].quantity").value(quantity1))
        .andExpect(jsonPath("cart[1].productId").value(productId2))
        .andExpect(jsonPath("cart[1].quantity").value(quantity2));
  }

  @DisplayName("장바구니 비우기 성공 테스트")
  @Test
  void clearCart() throws Exception {
    // given
    given(cartService.clearCart()).willReturn(List.of());

    // when
    mockMvc.perform(put("/carts")
            .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("cart").isEmpty());
  }

  @DisplayName("장바구니 개별 상품 삭제 성공 테스트")
  @Test
  void removeProduct() throws Exception {
    // given
    Long productId = 1L;
    given(cartService.removeProduct(productId)).willReturn(List.of());

    // when
    mockMvc.perform(delete("/carts/products/{productId}", productId))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("cart").isEmpty());
  }
}
