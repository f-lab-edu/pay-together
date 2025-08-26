package com.paytogether.product.repository;

import com.paytogether.product.service.result.RegisterEventProductResult;
import java.time.Duration;
import java.util.Map;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class EventProductRedisRepository {

  private static final String TOTAL = "total";
  private static final String REMAINING = "remaining";
  private final RedisTemplate<String, String> redisEventProductTemplate;

  public EventProductRedisRepository(
      @Qualifier("redisEventProductTemplate") RedisTemplate<String, String> redisEventProductTemplate) {
    this.redisEventProductTemplate = redisEventProductTemplate;
  }

  public RegisterEventProductResult registerEventProduct(String key, int quantity,
      long ttlSeconds) {
    HashOperations<String, String, String> hashOps = redisEventProductTemplate.opsForHash();
    hashOps.put(key, TOTAL, String.valueOf(quantity));
    hashOps.put(key, REMAINING, String.valueOf(quantity));
    redisEventProductTemplate.expire(key, Duration.ofSeconds(ttlSeconds));
    Map<String, String> entries = hashOps.entries(key);

    return new RegisterEventProductResult(key, Integer.valueOf(entries.get(TOTAL)),
        Integer.valueOf(entries.get(REMAINING)), ttlSeconds);
  }
}
