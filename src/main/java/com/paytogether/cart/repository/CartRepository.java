package com.paytogether.cart.repository;

import com.paytogether.cart.service.CartProductDto;
import com.paytogether.member.service.HttpSessionManager;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class CartRepository {

  private static final String REDIS_CART_KEY = "CART";
  private final HttpSessionManager httpSessionManager;

  public void saveCart(Map<String, CartProductDto> cart) {
    httpSessionManager.set(REDIS_CART_KEY, cart);
  }

  public void clearCart(Map<String, CartProductDto> cart) {
    cart.clear();
    saveCart(cart);
  }

  public void removeProduct(Map<String, CartProductDto> cart, String productId) {
    cart.remove(productId);
    saveCart(cart);
  }

  public Map<String, CartProductDto> findCart() {
    Map<String, CartProductDto> cart = (Map<String, CartProductDto>) httpSessionManager.get(REDIS_CART_KEY);
    return cart != null ? cart : new HashMap<>();
  }
}
