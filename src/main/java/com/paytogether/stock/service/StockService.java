package com.paytogether.stock.service;

import com.paytogether.order.service.command.ProductQuantities;
import com.paytogether.order.service.command.ProductQuantity;
import com.paytogether.stock.entity.Stock;
import com.paytogether.stock.exception.StockErrors;
import com.paytogether.stock.exception.StockException;
import com.paytogether.stock.repository.StockRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class StockService {

  private final StockRepository stockRepository;

  public List<Stock> findStocksByProductIds(List<Long> productIds) {
    return stockRepository.findAllByProductIdIn(productIds);
  }

  public void decreaseStocks(ProductQuantities productQuantities, List<Stock> stocks) {
    for (ProductQuantity pq : productQuantities.getProductQuantities()) {
      Stock stock = findStockByProductId(stocks, pq.productId());
      stock.decreaseQuantity(pq.quantity());
    }
  }

  private Stock findStockByProductId(List<Stock> stocks, Long productId) {
    return stocks.stream()
        .filter(s -> s.getProductId().equals(productId))
        .findFirst()
        .orElseThrow(() -> new StockException(StockErrors.INVALID_PRODUCT_ID));
  }
}