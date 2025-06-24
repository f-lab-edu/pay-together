package com.paytogether.cart.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.paytogether.cart.service.result.CartResult;
import com.paytogether.member.entity.Gender;
import com.paytogether.member.entity.Member;
import com.paytogether.member.repository.MemberRepository;
import java.time.LocalDateTime;
import java.util.List;
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
  void before() {
    Member member = Member.createMember("pay@Spring.com", passwordEncoder.encode("password!"),
        "name", 100,
        Gender.MALE, "address", "01012345678", LocalDateTime.now());
    Member save = memberRepository.save(member);
    memberId = save.getId();
  }

  @AfterEach
  void after() {
    cartService.clearCart(memberId);
    memberRepository.deleteAll();
  }

  @DisplayName("카트에 상품을 추가하면 성공한다")
  @Test
  void addProductToCart() {
    // given
    // when
    CartResult cartResult = cartService.addNewProduct(memberId, 2L, 10);
    List<CartDto> cart = cartResult.getCart();
    // then
    assertThat(cart.get(0).getQuantity()).isEqualTo(10);
  }

  @DisplayName("장바구니에 담긴 상품을 하나 삭제하면 하나만 삭제된다")
  @Test
  void deleteProduct() {
    // given
    cartService.addNewProduct(memberId, 2L, 10);
    // when
    CartResult cartResult = cartService.deleteProduct(memberId, 2L);
    // then
    System.out.println(cartResult.getCart());
    assertThat(cartResult.getCart()).hasSize(0);
  }

  @DisplayName("클리어하면 회원의 장바구니에 담긴 상품들이 전부 삭제된다")
  @Test
  void clearCart() {
    // given
    cartService.addNewProduct(memberId, 2L, 10);
    cartService.addNewProduct(memberId, 3L, 10);
    // when
    CartResult cartResult = cartService.clearCart(memberId);
    // then
    assertThat(cartResult.getCart()).hasSize(0);
  }
}
