package com.paytogether.order.service.command;

import com.paytogether.product.entity.Product;
import com.paytogether.stock.entity.Stock;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
public class ProductQuantities {

  private final List<ProductQuantity> productQuantities;

  public ProductQuantities(List<ProductQuantity> productQuantities) {
    this.productQuantities = productQuantities;
  }

  public List<Long> getProductIdList() {
    return productQuantities.stream()
        .map(ProductQuantity::productId)
        .collect(Collectors.toList());
  }

  public ProductQuantities extractCanOrderProducts(List<Stock> stocks) {
    Map<Long, Integer> stockMap = stocks.stream()
        .collect(Collectors.toMap(Stock::getProductId, Stock::getQuantity));

    List<ProductQuantity> filtered = productQuantities.stream()
        .filter(pq -> {
          Integer stockQuantity = stockMap.get(pq.productId());
          return stockQuantity != null && stockQuantity >= pq.quantity();
        })
        .collect(Collectors.toList());

    return new ProductQuantities(filtered);
  }

  public int calTotalPrice(List<Product> products) {
    Map<Long, Integer> productIdQuantityMap = productQuantities.stream()
        .collect(Collectors.toMap(
            ProductQuantity::productId,
            ProductQuantity::quantity
        ));

    return products.stream()
        .mapToInt(p -> p.getPrice() * productIdQuantityMap.get(p.getId()))
        .sum();
  }
}
