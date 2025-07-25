package com.paytogether.stock.entity;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class StockTest {

  @DisplayName("재고 감소 성공 테스트")
  @Test
  void decreaseQuantity() {
    // given
    Stock stock = Stock.create(1L, 10, LocalDateTime.now());
    // when
    stock.decreaseQuantity(5);
    // then
    assertThat(stock.getQuantity()).isEqualTo(5);
  }

  @DisplayName("재고 수량 부족 테스트")
  @Test
  void quantityLessThan() {
    // given
    Stock stock = Stock.create(1L, 10, LocalDateTime.now());
    // when
    boolean result = stock.quantityLessThan(11);
    // then
    assertThat(result).isTrue();
  }
}
