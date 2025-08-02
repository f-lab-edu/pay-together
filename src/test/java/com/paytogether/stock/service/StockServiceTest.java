package com.paytogether.stock.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.paytogether.order.service.command.ProductQuantities;
import com.paytogether.order.service.command.ProductQuantity;
import com.paytogether.stock.entity.Stock;
import com.paytogether.stock.repository.StockRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
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

  @DisplayName("재고 감소")
  @Test
  void decreaseStock() {
    // given
    Long productId1 = 1L, productId2 = 2L;;
    int quantity1 = 10, quantity2 = 20;
    Stock stock1 = Stock.create(productId1, quantity1, LocalDateTime.now());
    Stock stock2 = Stock.create(productId2, quantity2, LocalDateTime.now());
    List<Stock> stocks = List.of(stock1, stock2);

    int orderQuantity1 = 5, orderQuantity2 = 10;
    ProductQuantities productQuantities = new ProductQuantities(List.of(
            new ProductQuantity(productId1, orderQuantity1),
            new ProductQuantity(productId2, orderQuantity2))
    );
    // when
    stockService.decreaseStocks(productQuantities, stocks);
    // then
    assertThat(stock1.getQuantity()).isEqualTo(quantity1 - orderQuantity1);
  }
}
