package com.paytogether.cart.repository;

import java.util.Map;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CartRepository {

  private final RedisTemplate<String, String> redisCartTemplate;

  public CartRepository(
      @Qualifier("redisCartTemplate") RedisTemplate<String, String> redisCartTemplate) {
    this.redisCartTemplate = redisCartTemplate;
  }

  public Map<String, String> saveProduct(String key, Long productId, int quantity) {
    HashOperations<String, String, String> hashOperations = redisCartTemplate.opsForHash();
    hashOperations.put(key, productId.toString(), String.valueOf(quantity));
    return hashOperations.entries(key);
  }

  public Map<String, String> getCart(String key) {
    HashOperations<String, String, String> hashOperations = redisCartTemplate.opsForHash();
    return hashOperations.entries(key);
  }

  public Map<String, String> clearCart(String key) {
    HashOperations<String, String, String> hashOperations = redisCartTemplate.opsForHash();
    Map<String, String> cart = hashOperations.entries(key);
    if (!cart.isEmpty()) {
      hashOperations.delete(key, cart.keySet().toArray());
    }
    return hashOperations.entries(key);
  }

  public Map<String, String> removeProduct(String key, Long productId) {
    HashOperations<String, String, String> hashOperations = redisCartTemplate.opsForHash();
    hashOperations.delete(key, productId.toString());
    return hashOperations.entries(key);
  }

}
