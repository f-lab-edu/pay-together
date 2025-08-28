package com.paytogether.cart.controller;

import com.paytogether.cart.controller.request.AddCartProductRequest;
import com.paytogether.cart.controller.request.AddEventProductRequest;
import com.paytogether.cart.controller.response.CartResponse;
import com.paytogether.cart.service.CartService;
import com.paytogether.cart.service.EventCartService;
import com.paytogether.cart.service.result.CartProductResult;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/carts")
public class CartController {

  private final CartService cartService;
  private final EventCartService eventCartService;

  @PostMapping
  public CartResponse saveProduct(@RequestBody AddCartProductRequest request) {
    List<CartProductResult> cartProductResults = cartService.saveProduct(request.productId(),
        request.quantity());
    return CartResponse.from(cartProductResults);
  }

  @PostMapping("/event")
  public boolean addEventProduct(@RequestBody AddEventProductRequest request) {
    return eventCartService.addEventProduct(request.productId(), request.quantity());
  }

  @PutMapping
  public CartResponse clearCart() {
    List<CartProductResult> cartProductResults = cartService.clearCart();
    return CartResponse.from(cartProductResults);
  }


  @DeleteMapping("/products/{productId}")
  public CartResponse removeProduct(@PathVariable Long productId) {
    List<CartProductResult> cartProductResults = cartService.removeProduct(productId);
    return CartResponse.from(cartProductResults);
  }


  @GetMapping
  public CartResponse getCart() {
    List<CartProductResult> cartProductResults = cartService.getCart();
    return CartResponse.from(cartProductResults);
  }
}
