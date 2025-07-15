package com.paytogether.stock.repository;

import com.paytogether.stock.entity.Stock;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<Stock, Long> {

  List<Stock> findAllByProductIdIn(List<Long> productIds);

  Stock findByProductId(Long productId);
}
