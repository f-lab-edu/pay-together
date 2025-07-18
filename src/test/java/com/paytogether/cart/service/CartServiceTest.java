package com.paytogether.cart.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.paytogether.cart.service.result.CartProductResult;
import com.paytogether.member.entity.Gender;
import com.paytogether.member.entity.Member;
import com.paytogether.member.repository.MemberRepository;
import com.paytogether.auth.service.LoginService;
import com.paytogether.product.entity.Product;
import com.paytogether.product.entity.ProductSellingStatus;
import com.paytogether.product.entity.ProductType;
import com.paytogether.product.repository.ProductRepository;
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
  private ProductRepository productRepository;

  @Autowired
  private LoginService loginService;

  @Autowired
  private PasswordEncoder passwordEncoder;

  private Long productId1;
  private Long productId2;

  @BeforeEach
  void before() {
    String password = "1234!678";
    String email = "pay@spring.com";
    Member member = Member.createMember(email, passwordEncoder.encode(password), "name", 100,
        Gender.MALE,
        "address", "01012345678", LocalDateTime.now());
    memberRepository.save(member);
    loginService.login(email, password);

    Product p1 = productRepository.save(Product.create(ProductType.HOME_APPLIANCES, ProductSellingStatus.SELLING, "가전제품", 1000, 100, LocalDateTime.now()));
    Product p2 = productRepository.save(Product.create(ProductType.FOOD, ProductSellingStatus.SELLING, "식품", 1000, 100, LocalDateTime.now()));

    productId1 = p1.getId();
    productId2 = p2.getId();
  }

  @AfterEach
  void after() {
    cartService.clearCart();
    memberRepository.deleteAll();
    productRepository.deleteAll();
  }

  @DisplayName("카트에 상품을 추가하면 성공한다")
  @Test
  void addProductToCart() {
    // given
    // when
    List<CartProductResult> cartProductResults = cartService.saveProduct(productId1, 10);
    // then
    assertThat(cartProductResults.get(0).quantity()).isEqualTo(10);
  }

  @DisplayName("장바구니에 담긴 상품을 하나 삭제하면 하나만 삭제된다")
  @Test
  void deleteProduct() {
    // given
    cartService.saveProduct(productId1, 10);
    // when
    List<CartProductResult> cartProductResults = cartService.removeProduct(productId1);
    // then
    assertThat(cartProductResults).hasSize(0);
  }

  @DisplayName("클리어하면 회원의 장바구니에 담긴 상품들이 전부 삭제된다")
  @Test
  void clearCart() {
    // given
    cartService.saveProduct(productId1, 10);
    cartService.saveProduct(productId2, 10);
    // when
    List<CartProductResult> cartProductResults = cartService.clearCart();
    // then
    assertThat(cartProductResults).hasSize(0);
  }
}
