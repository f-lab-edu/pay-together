package com.paytogether.product.service;

import static com.paytogether.product.exception.ProductErrors.PRODUCT_NOT_FOUND;

import com.paytogether.product.entity.EventProduct;
import com.paytogether.product.exception.ProductException;
import com.paytogether.product.repository.EventProductRedisRepository;
import com.paytogether.product.repository.ProductRepository;
import com.paytogether.product.service.command.CreateEventProductCommand;
import com.paytogether.product.service.result.CreateEventProductResult;
import com.paytogether.product.service.result.EventProductResult;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AdminProductService {

  private final ProductRepository productRepository;
  private final EventProductRedisRepository eventProductRedisRepository;

  @Transactional
  public CreateEventProductResult createEventProduct(CreateEventProductCommand command,
      LocalDateTime now) {
    EventProduct eventProduct = command.toEntity(now);
    EventProduct savedEventProduct = productRepository.save(eventProduct);
    return CreateEventProductResult.from(savedEventProduct);
  }

  public EventProductResult registerEventProduct(Long productId, int totalQuantity,
      long ttlSeconds) {
    String key = EventProductKeyUtil.getProductKey(productId);
    return eventProductRedisRepository.registerEventProduct(key, totalQuantity, ttlSeconds);
  }

  public boolean existsById(Long productId) {
    return productRepository.existsById(productId);
  }

  public void deleteAllEventProduct() {
    String key = EventProductKeyUtil.getAllKey();
    eventProductRedisRepository.deleteAllEventProduct(key);
  }
}
