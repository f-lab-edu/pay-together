package com.paytogether.cart.service;

import com.paytogether.cart.service.result.CartResult;
import com.paytogether.exception.PayTogetherErrors;
import com.paytogether.exception.PayTogetherException;
import com.paytogether.member.repository.MemberRepository;
import java.time.Duration;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class CartService {

  private static final String CART_KEY = "cart:";
  private final RedisTemplate<String, String> redisTemplate;
  private final MemberRepository memberRepository;

  public CartResult addProductToCart(Long memberId, Long productId, int quantity) {
    validateMemberId(memberId);
    String key = CART_KEY + memberId;
    HashOperations<String, String, String> cartHash = redisTemplate.opsForHash();
    cartHash.put(key, productId.toString(), String.valueOf(quantity));
    log.info("{}, {}", key, cartHash.entries(key));
    redisTemplate.expire(key, Duration.ofDays(7));
    return CartResult.of(cartHash.entries(key));
  }

  public CartResult getCart(Long memberId) {
    validateMemberId(memberId);
    String key = CART_KEY + memberId;
    HashOperations<String, String, String> cartHash = redisTemplate.opsForHash();
    return CartResult.of(cartHash.entries(key));
  }

  public CartResult deleteProduct(Long memberId, Long productId) {
    validateMemberId(memberId);
    String key = CART_KEY + memberId;
    HashOperations<String, String, String> cartHash = redisTemplate.opsForHash();
    cartHash.delete(key, productId.toString());
    return CartResult.of(cartHash.entries(key));
  }

  public CartResult clearCart(Long memberId) {
    validateMemberId(memberId);
    String key = CART_KEY + memberId;
    HashOperations<String, String, String> cartHash = redisTemplate.opsForHash();
    Map<String, String> cart = cartHash.entries(key);
    if (!cart.isEmpty()) {
      cartHash.delete(key, cart.keySet().toArray());
    }
    return CartResult.of(cartHash.entries(key));
  }

  public void validateMemberId(Long memberId) {
    memberRepository.findById(memberId)
        .orElseThrow(() -> new PayTogetherException(PayTogetherErrors.MEMBER_NOT_FOUND));
  }
}
