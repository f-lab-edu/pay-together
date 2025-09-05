package com.paytogether.product.service;

import static org.assertj.core.api.Assertions.tuple;

import com.paytogether.product.repository.EventProductRedisRepository;
import com.paytogether.product.repository.ProductRepository;
import com.paytogether.product.service.result.EventProductResult;
import java.util.List;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ProductServiceTest {

  @Autowired
  private ProductRepository productRepository;

  @Autowired
  private EventProductRedisRepository eventProductRedisRepository;

  @BeforeEach
  void before() {
    eventProductRedisRepository.deleteAllEventProduct("event:product:*");
  }

  @DisplayName("이벤트 상품 조회 성공 테스트")
  @Test
  void test() {
    // given
    final String key = "event:product:*";
    final String key1 = "event:product:123";
    final String key2 = "event:product:456";
    eventProductRedisRepository.registerEventProduct(key1, 100, 3600);
    eventProductRedisRepository.registerEventProduct(key2, 200, 3600);
    // when
    List<EventProductResult> allEventProductList = eventProductRedisRepository.findAllEventProductList(
        key);
    // then
    for (EventProductResult epr : allEventProductList) {
      System.out.println(epr.toString());
    }
    SoftAssertions softAssertions = new SoftAssertions();
    softAssertions.assertThat(allEventProductList).hasSize(2);
    softAssertions.assertThat(allEventProductList)
        .extracting("key", "totalQuantity", "remainQuantity", "ttlSeconds")
        .containsExactlyInAnyOrder(
            tuple(key1, 100, 100, 3600),
            tuple(key2, 200, 200, 3600)
        );
  }
}