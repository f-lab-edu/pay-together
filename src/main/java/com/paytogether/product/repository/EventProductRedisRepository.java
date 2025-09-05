package com.paytogether.product.repository;

import com.paytogether.product.service.result.EventProductResult;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
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

  public EventProductResult registerEventProduct(String key, int quantity, long ttlSeconds) {
    HashOperations<String, String, String> hashOps = redisEventProductTemplate.opsForHash();
    hashOps.put(key, TOTAL, String.valueOf(quantity));
    hashOps.put(key, REMAINING, String.valueOf(quantity));
    redisEventProductTemplate.expire(key, Duration.ofSeconds(ttlSeconds));
    Map<String, String> entries = hashOps.entries(key);

    return new EventProductResult(key, Integer.valueOf(entries.get(TOTAL)),
        Integer.valueOf(entries.get(REMAINING)), ttlSeconds);
  }

  public List<EventProductResult> findAllEventProductList(String key) {
    HashOperations<String, String, String> hashOps = redisEventProductTemplate.opsForHash();
    Set<String> keys = redisEventProductTemplate.keys(key);
    List<EventProductResult> result = new ArrayList<>();
    for (String redisKey : keys) {
      List<String> values = hashOps.multiGet(redisKey, Arrays.asList(TOTAL, REMAINING));
      int totalQuantity = Integer.parseInt(values.get(0));
      int remaining = Integer.parseInt(values.get(1));
      Long ttlSeconds = redisEventProductTemplate.getExpire(redisKey);

      result.add(new EventProductResult(redisKey, totalQuantity, remaining, ttlSeconds));
    }
    return result;
  }

  public void deleteAllEventProduct(String key) {
    Set<String> keys = redisEventProductTemplate.keys("event:product:*");
    if (keys != null && !keys.isEmpty()) {
      redisEventProductTemplate.delete(keys);
    }
  }
}
