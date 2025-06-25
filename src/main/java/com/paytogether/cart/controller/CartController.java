package com.paytogether.cart.controller;

import com.paytogether.cart.controller.request.AddCartProductRequest;
import com.paytogether.cart.controller.request.AdjustCartProductRequest;
import com.paytogether.cart.controller.response.CartResponse;
import com.paytogether.cart.service.CartService;
import com.paytogether.cart.service.result.CartResult;
import com.paytogether.common.api.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/members/{memberId}/carts")
public class CartController {

  private final CartService cartService;

  @PostMapping
  public ApiResponse<CartResponse> addNewProduct(@PathVariable Long memberId,
      @RequestBody AddCartProductRequest request) {
    CartResult cartResult = cartService.addNewProduct(memberId, request.productId(),
        request.quantity());
    return ApiResponse.createResponse(HttpStatus.OK.value(), CartResponse.of(cartResult.getCartProductDtoList()));
  }

  @PatchMapping
  public ApiResponse<CartResponse> adjustProduct(@PathVariable Long memberId,
      @RequestBody AdjustCartProductRequest request) {
    CartResult cartResult = cartService.adjustProduct(memberId, request.productId(),
        request.quantity());
    return ApiResponse.createResponse(HttpStatus.OK.value(), CartResponse.of(cartResult.getCartProductDtoList()));
  }

  @PutMapping
  public ApiResponse<CartResponse> clearCart(@PathVariable Long memberId) {
    CartResult cartResult = cartService.clearCart(memberId);
    return ApiResponse.createResponse(HttpStatus.OK.value(), CartResponse.of(cartResult.getCartProductDtoList()));
  }

  @DeleteMapping("/products/{productId}")
  public ApiResponse<CartResponse> deleteProduct(@PathVariable Long memberId,
      @PathVariable Long productId) {
    CartResult cartResult = cartService.deleteProduct(memberId, productId);
    return ApiResponse.createResponse(HttpStatus.OK.value(), CartResponse.of(cartResult.getCartProductDtoList()));
  }

  @GetMapping
  public ApiResponse<CartResponse> getCart(@PathVariable Long memberId) {
    CartResult cartResult = cartService.getCart(memberId);
    return ApiResponse.createResponse(HttpStatus.OK.value(), CartResponse.of(cartResult.getCartProductDtoList()));
  }
}
