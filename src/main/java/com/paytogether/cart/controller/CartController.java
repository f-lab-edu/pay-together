package com.paytogether.cart.controller;

import com.paytogether.cart.controller.request.AddCartProductRequest;
import com.paytogether.cart.controller.response.CartResponse;
import com.paytogether.cart.service.CartService;
import com.paytogether.cart.service.result.CartResult;
import com.paytogether.common.api.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
@RequestMapping("/carts")
public class CartController {

  private final CartService cartService;

  @PostMapping
  public ApiResponse<CartResponse> saveProduct(@RequestBody AddCartProductRequest request) {
    CartResult cartResult = cartService.saveProduct(request.productId(),
        request.quantity());
    return ApiResponse.createResponse(HttpStatus.OK.value(),
        CartResponse.of(cartResult.getCartProductDtoList()));
  }

  @PutMapping
  public ApiResponse<CartResponse> clearCart() {
    CartResult cartResult = cartService.clearCart();
    return ApiResponse.createResponse(HttpStatus.OK.value(),
        CartResponse.of(cartResult.getCartProductDtoList()));
  }

  @DeleteMapping("/products/{productId}")
  public ApiResponse<CartResponse> removeProduct(@PathVariable Long productId) {
    CartResult cartResult = cartService.removeProduct(productId);
    return ApiResponse.createResponse(HttpStatus.OK.value(),
        CartResponse.of(cartResult.getCartProductDtoList()));
  }

  @GetMapping
  public ApiResponse<CartResponse> findCart() {
    CartResult cartResult = cartService.findCart();
    return ApiResponse.createResponse(HttpStatus.OK.value(),
        CartResponse.of(cartResult.getCartProductDtoList()));
  }
}
