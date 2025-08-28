package com.paytogether.cart.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.paytogether.auth.service.HttpSessionManager;
import com.paytogether.cart.repository.CartRepository;
import com.paytogether.common.TestLoginHelper;
import com.paytogether.product.repository.EventProductRedisRepository;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest
class EventCartServiceTest {

  @Autowired
  private EventCartService eventCartService;

  @Autowired
  private RedisTemplate<String, String> redisEventProductTemplate;

  @Autowired
  private TestLoginHelper loginHelper;

  @Autowired
  private EventProductRedisRepository eventProductRedisRepository;

  @Autowired
  private CartRepository cartRepository;

  @MockitoBean
  private HttpSessionManager httpSessionManager;

  private String key = "event:product:1";

  @BeforeEach
  void before() {
    loginHelper.loginAsTestUser();
    eventProductRedisRepository.deleteAllEventProduct("event:product:*");
    cartRepository.clearCart("cart:userid:1");

    HashOperations<String, String, String> hashOps = redisEventProductTemplate.opsForHash();
    hashOps.put(key, "total", String.valueOf(100));
    hashOps.put(key, "remaining", String.valueOf(100));
  }

  @DisplayName("이벤트 상품 카트 재고 감소 성공 테스트")
  @Test
  void addEventProductDecrease() {
    // given
    // when
    eventCartService.addEventProduct(1L, 2);
    // then
    long remaining = Long.parseLong(
        (String) redisEventProductTemplate.opsForHash().get(key, "remaining"));
    assertThat(remaining).isEqualTo(98);
  }

  @DisplayName("이벤트 상품 카트 동시성 테스트 - 100명이 동시에 요청하면 재고 0이 남아야 한다")
  @Test
  void addEventProductDecreaseConcurrency() throws InterruptedException {
    // given
    int threadCount = 100;
    ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
    CountDownLatch latch = new CountDownLatch(threadCount);

    ThreadLocal<Long> threadLocalUserId = new ThreadLocal<>();
    Mockito.when(httpSessionManager.getMemberId())
        .thenAnswer(invocation -> threadLocalUserId.get());

    // when
    for (int i = 1; i <= threadCount; i++) {
      final long userId = i;
      executorService.submit(() -> {
        try {
          threadLocalUserId.set(userId);
          eventCartService.addEventProduct(1L, 1);
        } finally {
          threadLocalUserId.remove();
          latch.countDown();
        }
      });
    }
    latch.await();
    executorService.shutdown();

    // then
    long remaining = Long.parseLong(
        (String) redisEventProductTemplate.opsForHash().get(key, "remaining"));
    assertThat(remaining).isEqualTo(0);
  }
}