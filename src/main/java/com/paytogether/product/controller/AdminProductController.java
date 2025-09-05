package com.paytogether.product.controller;

import com.paytogether.product.controller.request.RegisterEventProductRequest;
import com.paytogether.product.controller.response.EventProductResponse;
import com.paytogether.product.service.AdminProductService;
import com.paytogether.product.service.result.EventProductResult;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/admin/products/event")
public class AdminProductController {

  private final AdminProductService adminProductService;

  @PostMapping
  public List<EventProductResponse> registerEventProduct(@RequestBody RegisterEventProductRequest request) {
    EventProductResult eventProductResult = adminProductService.registerEventProduct(
        request.productId(), request.totalQuantity(),
        request.ttlSeconds());
    return EventProductResponse.from(List.of(eventProductResult));
  }

  @DeleteMapping
  public void deleteAllEventProduct() {
    adminProductService.deleteAllEventProduct();
  }
}
