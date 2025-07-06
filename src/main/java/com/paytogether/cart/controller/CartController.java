package com.paytogether.cart.controller;

import com.paytogether.cart.controller.request.AddCartProductRequest;
import com.paytogether.cart.controller.response.CartResponse;
import com.paytogether.cart.service.CartService;
import com.paytogether.cart.service.result.CartProductResult;
import com.paytogether.common.api.response.ApiResponse;
import java.util.List;
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
    List<CartProductResult> cartProductResults = cartService.saveProduct(request.productId(),
        request.quantity());
    return ApiResponse.createResponse(HttpStatus.OK.value(),
        CartResponse.from(cartProductResults));
  }

  @PutMapping
  public ApiResponse<CartResponse> clearCart() {
    List<CartProductResult> cartProductResults = cartService.clearCart();
    return ApiResponse.createResponse(HttpStatus.OK.value(),
        CartResponse.from(cartProductResults));
  }

  @DeleteMapping("/products/{productId}")
  public ApiResponse<CartResponse> removeProduct(@PathVariable Long productId) {
    List<CartProductResult> cartProductResults = cartService.removeProduct(productId);
    return ApiResponse.createResponse(HttpStatus.OK.value(),
        CartResponse.from(cartProductResults));
  }

  @GetMapping
  public ApiResponse<CartResponse> getCart() {
    List<CartProductResult> cartProductResults = cartService.getCart();
    return ApiResponse.createResponse(HttpStatus.OK.value(),
        CartResponse.from(cartProductResults));
  }
}
