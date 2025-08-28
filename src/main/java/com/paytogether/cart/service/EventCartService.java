package com.paytogether.cart.service;

import com.paytogether.auth.service.HttpSessionManager;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class EventCartService {

  private static final String CART_KEY = "cart:userid:";
  private static final String EVENT_PRODUCT = "event:product:";
  private static final String EVENT_USER_SET = "event:product:%d:users";
  private final StringRedisTemplate redisTemplate;
  private final DefaultRedisScript<Long> decrementRemainingScript;
  private final HttpSessionManager httpSessionManager;

  public boolean addEventProduct(Long productId, int quantity) {
    String eventProductKey = EVENT_PRODUCT + productId;
    Long memberId = getMemberIdInSession();
    String cartKey = CART_KEY + memberId;
    String userSetKey = String.format(EVENT_USER_SET, productId);

    Long result = redisTemplate.execute(
        decrementRemainingScript,
        Arrays.asList(eventProductKey, cartKey, userSetKey),
        productId.toString(),
        String.valueOf(quantity),
        memberId.toString()
    );

    return result == 1 ? true : false;
  }

  private Long getMemberIdInSession() {
    return httpSessionManager.getMemberId();
  }
}
