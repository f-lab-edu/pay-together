package com.paytogether.stock.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import com.paytogether.stock.entity.Stock;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class StockRepositoryTest {

  @Autowired
  private StockRepository stockRepository;

  @DisplayName("상품 아이디로 재고 검색 성공 테스트")
  @Test
  void findAllByProductIdIn() {
    // given
    Stock stock1 = Stock.create(1L, 10, LocalDateTime.now());
    Stock stock2 = Stock.create(2L, 20, LocalDateTime.now());
    Stock stock3 = Stock.create(3L, 30, LocalDateTime.now());
    stockRepository.saveAll(List.of(stock1, stock2, stock3));
    // when
    List<Stock> stocks = stockRepository.findAllByProductIdIn(List.of(1L, 2L));
    // then
    SoftAssertions softAssertions = new SoftAssertions();
    softAssertions.assertThat(stocks).hasSize(2);
    softAssertions.assertThat(stocks).extracting("productId", "quantity")
        .containsExactlyInAnyOrder(tuple(1L, 10), tuple(2L, 20));
    softAssertions.assertAll();
  }

  @DisplayName("단건 상품 아이디 재고 검색 성공 테스트")
  @Test
  void findByProductId() {
    //given
    Long productId= 1L;
    Stock stock1 = Stock.create(productId, 10, LocalDateTime.now());
    stockRepository.save(stock1);
    // when
    Optional<Stock> stock = stockRepository.findByProductId(productId);
    // then
    assertThat(stock).isNotNull();
  }
}