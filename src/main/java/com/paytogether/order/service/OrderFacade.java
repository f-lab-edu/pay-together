package com.paytogether.order.service;

import com.paytogether.order.service.command.OrderCreateCommand;
import com.paytogether.order.service.command.ProductQuantities;
import com.paytogether.order.service.command.ProductQuantity;
import com.paytogether.order.service.result.OrderCreateResult;
import com.paytogether.product.entity.Product;
import com.paytogether.product.service.ProductService;
import com.paytogether.stock.entity.Stock;
import com.paytogether.stock.service.StockService;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class OrderFacade {

  private final StockService stockService;

  private final ProductService productService;

  private final OrderService orderService;

  @Transactional
  public OrderCreateResult order(OrderCreateCommand command, LocalDateTime orderedAt) {
    List<ProductQuantity> productQuantityList = command.prductQuantityList();
    ProductQuantities productQuantities = new ProductQuantities(productQuantityList);

    List<Long> productIds = productQuantities.getProductIdList();
    List<Product> products = productService.findProductsByIds(productIds);
    ProductQuantities updateProductQuantities = productQuantities.updateProductPrice(products);

    List<Stock> stocks = stockService.findStocksByProductIds(productIds);
    ProductQuantities availableProductQuantities = updateProductQuantities.filterByStock(stocks);
    stockService.decreaseStocks(availableProductQuantities, stocks);

    return orderService.order(availableProductQuantities, orderedAt);
  }
}
