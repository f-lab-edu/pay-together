package com.paytogether.cart.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paytogether.cart.controller.request.AddCartProductRequest;
import com.paytogether.cart.service.CartProductDto;
import com.paytogether.cart.service.CartService;
import com.paytogether.cart.service.result.CartResult;
import com.paytogether.config.TestSecurityConfig;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@Import(TestSecurityConfig.class)
@WebMvcTest(CartController.class)
class CartControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private CartService cartService;

  @Autowired
  private ObjectMapper objectMapper;

  @DisplayName("상품을 장바구니에 추가 요청 성공 테스트")
  @Test
  void saveProduct() throws Exception {
    // given
    Long productId = 1L;
    int quantity = 10;
    Map<Long, CartProductDto> cart = new HashMap<>();
    CartProductDto cartProductDto = CartProductDto.create(productId, quantity);
    cart.put(productId, cartProductDto);
    given(cartService.saveProduct(productId, quantity)).willReturn(CartResult.of(cart));

    AddCartProductRequest request = new AddCartProductRequest(productId, quantity);

    // when
    mockMvc.perform(post("/carts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("body.cartProductDtoList[0].productId").value(productId))
        .andExpect(jsonPath("body.cartProductDtoList[0].quantity").value(quantity));
  }

  @DisplayName("장바구니 조회 성공 테스트")
  @Test
  void findCart() throws Exception {
    // given
    Long productId1 = 1L;
    int quantity1 = 10;
    CartProductDto cartProductDto1 = CartProductDto.create(productId1, quantity1);

    Long productId2 = 2L;
    int quantity2 = 20;
    CartProductDto cartProductDto2 = CartProductDto.create(productId2, quantity2);

    Map<Long, CartProductDto> cart = new HashMap<>();
    cart.put(productId1, cartProductDto1);
    cart.put(productId2, cartProductDto2);

    given(cartService.findCart()).willReturn(CartResult.of(cart));

    // when
    mockMvc.perform(get("/carts")
            .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("body.cartProductDtoList[0].productId").value(productId1))
        .andExpect(jsonPath("body.cartProductDtoList[0].quantity").value(quantity1))
        .andExpect(jsonPath("body.cartProductDtoList[1].productId").value(productId2))
        .andExpect(jsonPath("body.cartProductDtoList[1].quantity").value(quantity2));
  }

  @DisplayName("장바구니 비우기 성공 테스트")
  @Test
  void clearCart() throws Exception {
    // given
    Map<Long, CartProductDto> cart = new HashMap<>();
    given(cartService.clearCart()).willReturn(CartResult.of(cart));

    // when
    mockMvc.perform(put("/carts")
            .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("body.cartProductDtoList").isEmpty());
  }

  @DisplayName("장바구니 개별 상품 삭제 성공 테스트")
  @Test
  void removeProduct() throws Exception{
    // given
    Long productId = 1L;
    Map<Long, CartProductDto> cart = new HashMap<>();

    given(cartService.removeProduct(productId)).willReturn(CartResult.of(cart));

    // when
    mockMvc.perform(delete("/carts/products/{productId}", productId))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("body.cartProductDtoList").isEmpty());
  }
}
