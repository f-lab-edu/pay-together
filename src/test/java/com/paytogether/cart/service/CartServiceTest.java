package com.paytogether.cart.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.paytogether.cart.service.result.CartResult;
import com.paytogether.member.repository.MemberRepository;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CartServiceTest {

  @Autowired
  private CartService cartService;

  @Autowired
  private MemberRepository memberRepository;

  @AfterEach
  void after() {
    cartService.clearCart();
    memberRepository.deleteAll();
  }

  @DisplayName("카트에 상품을 추가하면 성공한다")
  @Test
  void addProductToCart() {
    // given
    // when
    CartResult cartResult = cartService.saveProduct(2L, 10);
    List<CartProductDto> cart = cartResult.getCartProductDtoList();
    // then
    assertThat(cart.get(0).getQuantity()).isEqualTo(10);
  }

  @DisplayName("장바구니에 담긴 상품을 하나 삭제하면 하나만 삭제된다")
  @Test
  void deleteProduct() {
    // given
    cartService.saveProduct(2L, 10);
    // when
    CartResult cartResult = cartService.removeProduct(2L);
    // then
    System.out.println(cartResult.getCartProductDtoList());
    assertThat(cartResult.getCartProductDtoList()).hasSize(0);
  }

  @DisplayName("클리어하면 회원의 장바구니에 담긴 상품들이 전부 삭제된다")
  @Test
  void clearCart() {
    // given
    cartService.saveProduct(2L, 10);
    cartService.saveProduct(3L, 10);
    // when
    CartResult cartResult = cartService.clearCart();
    // then
    assertThat(cartResult.getCartProductDtoList()).hasSize(0);
  }
}
