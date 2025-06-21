package com.paytogether.cart.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.paytogether.cart.service.result.CartResult;
import com.paytogether.member.entity.Gender;
import com.paytogether.member.entity.Member;
import com.paytogether.member.repository.MemberRepository;
import java.time.LocalDateTime;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class CartServiceTest {

  @Autowired
  private CartService cartService;

  @Autowired
  private MemberRepository memberRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  private Long memberId;

  @BeforeEach
  void delete() {
    Member member = Member.createMember("pay@Spring.com", passwordEncoder.encode("password!"),
        "name", 100,
        Gender.MALE, "address", "01012345678", LocalDateTime.now());
    Member save = memberRepository.save(member);
    memberId = save.getId();
    cartService.clearCart(memberId);
  }

  @AfterEach
  void after() {
    memberRepository.deleteAll();
  }

  @DisplayName("카트에 상품을 추가하면 성공한다")
  @Test
  void addProductToCart() {
    // given
    // when
    CartResult cartResult = cartService.addProductToCart(memberId, 2L, 10);
    Map<String, String> cart = cartResult.getCart();
    // then
    assertThat(cart.get("2")).isEqualTo("10");
  }

  @DisplayName("장바구니에 담긴 상품을 하나 삭제하면 하나만 삭제된다")
  @Test
  void deleteProduct() {
    // given
    cartService.addProductToCart(memberId, 2L, 10);
    // when
    CartResult cartResult = cartService.deleteProduct(memberId, 2L);
    // then
    assertThat(cartResult.getCart()).hasSize(0);
  }

  @DisplayName("클리어하면 회원의 장바구니에 담긴 상품들이 전부 삭제된다")
  @Test
  void clearCart() {
    // given
    cartService.addProductToCart(memberId, 2L, 10);
    cartService.addProductToCart(memberId, 3L, 10);
    // when
    CartResult cartResult = cartService.clearCart(1L);
    // then
    assertThat(cartResult.getCart()).hasSize(0);
  }
}
