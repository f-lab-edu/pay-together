package com.paytogether.product.service;

import com.paytogether.product.entity.Product;
import com.paytogether.product.repository.ProductRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProductService {

  private final ProductRepository productRepository;

  public List<Product> findProductsByIds(List<Long> productIds) {
    return productRepository.findAllById(productIds);
  }
}