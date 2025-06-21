package com.paytogether.cart.controller;

import com.paytogether.cart.controller.request.AddProductRequest;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class CartController {

  private final CartService cartService;

  @PostMapping("/cart/add/{memberId}")
  public ApiResponse<CartResponse> addProduct(@PathVariable Long memberId, @RequestBody AddProductRequest request) {
    CartResult cartResult = cartService.addProductToCart(memberId, request.getProductId(),
        request.getQuantity());
    return ApiResponse.createResponse(HttpStatus.OK.value(), CartResponse.of(cartResult.getCart()));
  }

  @DeleteMapping("/cart/clear/{memberId}")
  public ApiResponse<CartResponse> clearCart(@PathVariable Long memberId) {
    CartResult cartResult = cartService.clearCart(memberId);
    return ApiResponse.createResponse(HttpStatus.OK.value(), CartResponse.of(cartResult.getCart()));
  }

  @DeleteMapping("/cart/delete/{memberId}/{productId}")
  public ApiResponse<CartResponse> deleteProduct(@PathVariable Long memberId, @PathVariable Long productId) {
    CartResult cartResult = cartService.deleteProduct(memberId, productId);
    return ApiResponse.createResponse(HttpStatus.OK.value(), CartResponse.of(cartResult.getCart()));
  }

  @GetMapping("/cart/{memberId}")
  public ApiResponse<CartResponse> getCart(@PathVariable Long memberId) {
    CartResult cartResult = cartService.getCart(memberId);
    return ApiResponse.createResponse(HttpStatus.OK.value(), CartResponse.of(cartResult.getCart()));
  }
}
