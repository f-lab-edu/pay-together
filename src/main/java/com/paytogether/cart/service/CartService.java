package com.paytogether.cart.service;

import com.paytogether.cart.exception.CartErrors;
import com.paytogether.cart.exception.CartException;
import com.paytogether.cart.repository.CartRepository;
import com.paytogether.cart.service.result.CartProductResult;
import com.paytogether.auth.service.HttpSessionManager;
import com.paytogether.product.repository.ProductRepository;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class CartService {

  private static final String CART_KEY = "cart:userid:";
  private final CartRepository cartRepository;
  private final ProductRepository productRepository;
  private final HttpSessionManager httpSessionManager;

  public List<CartProductResult> saveProduct(Long productId, int quantity) {
    validateProductId(productId);

    Long memberId = getMemberIdInSession();
    String key = generateKey(memberId);

    Map<String, String> cart = cartRepository.saveProduct(key, productId, quantity);

    return cart.entrySet().stream()
        .map(p -> new CartProductResult(Long.parseLong(p.getKey()), Integer.parseInt(p.getValue())))
        .toList();
  }

  public List<CartProductResult> getCart() {
    Long memberId = getMemberIdInSession();
    String key = generateKey(memberId);

    Map<String, String> cart = cartRepository.getCart(key);

    return cart.entrySet().stream()
        .map(p -> new CartProductResult(Long.parseLong(p.getKey()), Integer.parseInt(p.getValue())))
        .toList();
  }

  public List<CartProductResult> removeProduct(Long productId) {
    Long memberId = getMemberIdInSession();
    String key = generateKey(memberId);

    Map<String, String> cart = cartRepository.removeProduct(key, productId);

    return cart.entrySet().stream()
        .map(p -> new CartProductResult(Long.parseLong(p.getKey()), Integer.parseInt(p.getValue())))
        .toList();
  }

  public List<CartProductResult> clearCart() {
    Long memberId = getMemberIdInSession();
    String key = generateKey(memberId);

    Map<String, String> cart = cartRepository.clearCart(key);

    return cart.entrySet().stream()
        .map(p -> new CartProductResult(Long.parseLong(p.getKey()), Integer.parseInt(p.getValue())))
        .toList();
  }

  private Long getMemberIdInSession() {
    return httpSessionManager.getMemberId();
  }

  private String generateKey(Long memberId) {
    return CART_KEY + memberId;
  }

  private void validateProductId(Long productId) {
    if (!productRepository.existsById(productId)) {
      throw new CartException(CartErrors.INVALID_INFO);
    }
  }
}
