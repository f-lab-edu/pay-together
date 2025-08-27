package com.paytogether.product.service;

import com.paytogether.product.entity.EventProductStatus;
import com.paytogether.product.entity.ProductSellingStatus;
import com.paytogether.product.entity.ProductType;
import com.paytogether.product.repository.ProductRepository;
import com.paytogether.product.service.command.CreateEventProductCommand;
import com.paytogether.product.service.result.CreateEventProductResult;
import com.paytogether.product.service.result.EventProductResult;
import java.time.LocalDateTime;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AdminProductServiceTest {

  @Autowired
  private AdminProductService adminProductService;

  @Autowired
  private ProductRepository productRepository;

  @DisplayName("이벤트 상품 저장 성공 테스트")
  @Test
  void createEventProduct() {
    // given
    LocalDateTime createdAt = LocalDateTime.now();
    LocalDateTime startEventTime = createdAt.plusHours(1);
    LocalDateTime endEventTime = startEventTime.plusHours(1);
    CreateEventProductCommand command = new CreateEventProductCommand(ProductType.FOOD,
        ProductSellingStatus.SELLING, "FOOD", 1000, 500, startEventTime,
        endEventTime, EventProductStatus.WAITING);
    // when
    CreateEventProductResult eventProduct = adminProductService.createEventProduct(command,
        createdAt);
    // then
    SoftAssertions softAssertions = new SoftAssertions();
    softAssertions.assertThat(eventProduct.name()).isEqualTo("FOOD");
    softAssertions.assertThat(eventProduct.eventPrice()).isEqualTo(500);
    softAssertions.assertThat(eventProduct.price()).isEqualTo(1000);
    softAssertions.assertThat(eventProduct.startEventTime()).isEqualTo(startEventTime);
  }

  @DisplayName("이벤트 상품 redis 저장 성공 테스트")
  @Test
  void test() {
    // given
    Long productId = 1L;
    String key = "event:product:1";
    int totalQuantity = 100;
    long ttlSeconds = 3600;
    // when
    EventProductResult result = adminProductService.registerEventProduct(
        productId, totalQuantity, ttlSeconds);
    // then
    SoftAssertions softAssertions = new SoftAssertions();
    softAssertions.assertThat(result.key()).isEqualTo(key);
    softAssertions.assertThat(result.totalQuantity()).isEqualTo(totalQuantity);
    softAssertions.assertThat(result.remainQuantity()).isEqualTo(totalQuantity);
    softAssertions.assertThat(result.ttlSeconds()).isEqualTo(ttlSeconds);
  }
}