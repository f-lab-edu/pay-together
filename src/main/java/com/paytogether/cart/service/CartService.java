package com.paytogether.cart.service;

import com.paytogether.cart.repository.CartRepository;
import com.paytogether.cart.service.result.CartResult;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class CartService {

  private final CartRepository cartRepository;

  public CartResult saveProduct(Long productId, int quantity) {
    Map<String, CartProductDto> cart = cartRepository.findCart();
    String key = productId.toString();

    CartProductDto cartProductDto = CartProductDto.create(productId, quantity);
    cart.put(key, cartProductDto);
    cartRepository.saveCart(cart);

    return CartResult.of(convertKeyStringToLong(cart));
  }

  public CartResult findCart() {
    Map<String, CartProductDto> cart = cartRepository.findCart();
    return CartResult.of(convertKeyStringToLong(cart));
  }

  public CartResult deleteProduct(Long productId) {
    Map<String, CartProductDto> cart = cartRepository.findCart();
    cartRepository.removeProduct(cart, productId.toString());
    return CartResult.of(convertKeyStringToLong(cart));
  }

  public CartResult clearCart() {
    Map<String, CartProductDto> cart = cartRepository.findCart();
    cartRepository.clearCart(cart);
    return CartResult.of(convertKeyStringToLong(cart));
  }

  public Map<Long, CartProductDto> convertKeyStringToLong(Map<String, CartProductDto> stringKey) {
    return stringKey.entrySet().stream().collect(Collectors.toMap(
        entry -> Long.parseLong(entry.getKey()), Map.Entry::getValue));
  }
}
