package com.paytogether.stock.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.paytogether.order.service.command.ProductQuantities;
import com.paytogether.order.service.command.ProductQuantity;
import com.paytogether.stock.entity.Stock;
import com.paytogether.stock.exception.StockException;
import com.paytogether.stock.repository.StockRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class StockServiceTest {

  @InjectMocks
  private StockService stockService;

  @Mock
  private StockRepository stockRepository;

  @DisplayName("재고 감소 성공 테스트")
  @Test
  void decreaseStock() {
    // given
    Long productId1 = 1L, productId2 = 2L;
    ;
    int quantity1 = 10, quantity2 = 20;
    Stock stock1 = Stock.create(productId1, quantity1, LocalDateTime.now());
    Stock stock2 = Stock.create(productId2, quantity2, LocalDateTime.now());
    List<Stock> stocks = List.of(stock1, stock2);

    int orderQuantity1 = 5, orderQuantity2 = 10;
    ProductQuantities productQuantities = new ProductQuantities(List.of(
        ProductQuantity.withoutPrice(productId1, orderQuantity1),
        ProductQuantity.withoutPrice(productId2, orderQuantity2))
    );
    // when
    stockService.decreaseStocks(productQuantities, stocks);
    // then
    assertThat(stock1.getQuantity()).isEqualTo(quantity1 - orderQuantity1);
  }

  @DisplayName("재고 감소 실패 테스트")
  @Test
  void decreaseStockFail() {
    // given
    Long productId1 = 1L;
    int quantity1 = 10;
    Stock stock1 = Stock.create(productId1, quantity1, LocalDateTime.now());
    List<Stock> stocks = List.of(stock1);

    int orderQuantity1 = 11;
    ProductQuantities productQuantities = new ProductQuantities(
        List.of(ProductQuantity.withoutPrice(productId1, orderQuantity1)));
    // when // then
    assertThatThrownBy(() -> stockService.decreaseStocks(productQuantities, stocks))
        .isInstanceOf(StockException.class)
        .hasMessage("재고 수량이 부족합니다");
  }
}
