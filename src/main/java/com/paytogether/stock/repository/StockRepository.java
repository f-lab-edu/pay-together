package com.paytogether.stock.repository;

import com.paytogether.stock.entity.Stock;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<Stock, Long> {

  List<Stock> findAllByProductIdIn(List<Long> productIds);

  Optional<Stock> findByProductId(Long productId);
}
