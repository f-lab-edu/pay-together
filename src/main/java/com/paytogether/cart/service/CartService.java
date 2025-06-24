package com.paytogether.cart.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paytogether.cart.service.result.CartResult;
import com.paytogether.exception.PayTogetherErrors;
import com.paytogether.exception.PayTogetherException;
import com.paytogether.member.repository.MemberRepository;
import com.paytogether.member.service.HttpSessionManager;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class CartService {

  private static final String REDIS_CART_KEY = "CART";
  private final MemberRepository memberRepository;
  private final HttpSessionManager httpSessionManager;
  private final ObjectMapper objectMapper;

  public CartResult addNewProduct(Long memberId, Long productId, int quantity) {
    validateMemberId(memberId);

    String key = productId.toString();
    Map<String, CartDto> cart = getCartInSession();
    if (cart == null) {
      cart = new HashMap<>();
    }
    if (cart.containsKey(key)) {
      throw new PayTogetherException(PayTogetherErrors.INVALID_INFO);
    }
    cart.put(key, CartDto.create(productId, quantity));
    httpSessionManager.set(REDIS_CART_KEY, cart);
    return CartResult.of(convertKeyStringToLong(cart));
  }

  public CartResult adjustProduct(Long memberId, Long productId, int quantity) {
    validateMemberId(memberId);

    String key = productId.toString();
    Map<String, CartDto> cart = getCartInSession();
    if (!cart.containsKey(key)) {
      throw new PayTogetherException(PayTogetherErrors.INVALID_INFO);
    }
    CartDto cartDto = objectMapper.convertValue(cart.get(productId.toString()), CartDto.class);
    cartDto.adjust(quantity);
    cart.put(key, cartDto);
    httpSessionManager.set(REDIS_CART_KEY, cart);
    return CartResult.of(convertKeyStringToLong(cart));
  }

  public CartResult getCart(Long memberId) {
    validateMemberId(memberId);

    Map<String, CartDto> cart = getCartInSession();
    return CartResult.of(convertKeyStringToLong(cart));
  }

  public CartResult deleteProduct(Long memberId, Long productId) {
    validateMemberId(memberId);

    Map<String, CartDto> cart = getCartInSession();
    cart.remove(productId.toString());
    httpSessionManager.set(REDIS_CART_KEY, cart);
    return CartResult.of(convertKeyStringToLong(cart));
  }

  public CartResult clearCart(Long memberId) {
    validateMemberId(memberId);

    Map<String, CartDto> cart = getCartInSession();
    cart.clear();
    httpSessionManager.set(REDIS_CART_KEY, cart);
    return CartResult.of(convertKeyStringToLong(cart));
  }

  private Map<String, CartDto> getCartInSession() {
    return (Map<String, CartDto>) httpSessionManager.get(REDIS_CART_KEY);
  }

  public void validateMemberId(Long memberId) {
    memberRepository.findById(memberId)
        .orElseThrow(() -> new PayTogetherException(PayTogetherErrors.MEMBER_NOT_FOUND));
  }

  public Map<Long, CartDto> convertKeyStringToLong(Map<String, CartDto> stringKey) {
    return stringKey.entrySet().stream().collect(Collectors.toMap(
        entry -> Long.parseLong(entry.getKey()), Map.Entry::getValue));
  }
}
