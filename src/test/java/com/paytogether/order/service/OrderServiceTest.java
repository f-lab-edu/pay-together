package com.paytogether.order.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import com.paytogether.auth.service.HttpSessionManager;
import com.paytogether.common.TestLoginHelper;
import com.paytogether.order.repository.OrderRepository;
import com.paytogether.order.service.command.ProductQuantities;
import com.paytogether.order.service.command.ProductQuantity;
import com.paytogether.order.service.result.OrderCreateResult;
import com.paytogether.product.entity.Product;
import com.paytogether.product.entity.ProductSellingStatus;
import com.paytogether.product.entity.ProductType;
import com.paytogether.product.repository.ProductRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class OrderServiceTest {

  @Autowired
  private OrderService orderService;

  @Autowired
  private OrderRepository orderRepository;

  @Autowired
  private HttpSessionManager httpSessionManager;

  @Autowired
  private TestLoginHelper loginHelper;

  @Autowired
  private ProductRepository productRepository;

  private List<Product> productList;
  @BeforeEach
  void before() {
    loginHelper.loginAsTestUser();
    Product product1 = Product.create(ProductType.FOOD, ProductSellingStatus.SELLING, "FISH", 1000,
        LocalDateTime.now());
    Product product2 = Product.create(ProductType.FOOD, ProductSellingStatus.SELLING, "APPLE", 2000,
        LocalDateTime.now());
    productList = List.of(product1, product2);
    productRepository.saveAll(productList);
  }

  @DisplayName("주문 성공 테스트")
  @Test
  void order() {
    // given
    List<ProductQuantity> productQuantityList = List.of(
        new ProductQuantity(1L, 10),
        new ProductQuantity(2L, 20)
    );
    ProductQuantities productQuantities = new ProductQuantities(productQuantityList);

    // when
    OrderCreateResult result = orderService.order(
        productQuantities,
        productList,
        LocalDateTime.now()
    );
    // then
    SoftAssertions softAssertions = new SoftAssertions();
    softAssertions.assertThat(result.orderTotalPrice()).isEqualTo(50000);
    softAssertions.assertThat(result.orderProductResults()).hasSize(2);
    softAssertions.assertThat(result.orderProductResults())
        .extracting("productId", "quantity", "totalPrice")
        .containsExactlyInAnyOrder(
            tuple(1L, 10, 10000),
            tuple(2L, 20, 40000)
        );
  }
}
