package com.paytogether.product.service;

import com.paytogether.product.entity.EventProduct;
import com.paytogether.product.repository.EventProductRedisRepository;
import com.paytogether.product.repository.ProductRepository;
import com.paytogether.product.service.command.CreateEventProductCommand;
import com.paytogether.product.service.result.CreateEventProductResult;
import com.paytogether.product.service.result.RegisterEventProductResult;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AdminProductService {

  private static final String EVENT_PRODUCT_KEY = "event:product:";
  private final ProductRepository productRepository;
  private final EventProductRedisRepository eventProductRedisRepository;

  @Transactional
  public CreateEventProductResult createEventProduct(CreateEventProductCommand command,
      LocalDateTime now) {
    EventProduct eventProduct = command.toEntity(now);
    EventProduct savedEventProduct = productRepository.save(eventProduct);
    return CreateEventProductResult.from(savedEventProduct);
  }

  public RegisterEventProductResult registerEventProduct(Long productId, int totalQuantity,
      long ttlSeconds) {
    String key = EVENT_PRODUCT_KEY + productId;
    return eventProductRedisRepository.registerEventProduct(key, totalQuantity, ttlSeconds);
  }
}
