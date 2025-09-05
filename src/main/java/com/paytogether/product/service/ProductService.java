package com.paytogether.product.service;

import com.paytogether.product.entity.Product;
import com.paytogether.product.repository.EventProductRedisRepository;
import com.paytogether.product.repository.ProductRepository;
import com.paytogether.product.service.result.EventProductResult;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProductService {

  private final ProductRepository productRepository;
  private final EventProductRedisRepository eventProductRedisRepository;

  public List<Product> findProductsByIds(List<Long> productIds) {
    return productRepository.findAllById(productIds);
  }

  public List<EventProductResult> findAllEventProducts() {
    String key = EventProductKeyUtil.getAllKey();
    return eventProductRedisRepository.findAllEventProductList(key);
  }
}