package com.paytogether.product.controller;

import com.paytogether.product.controller.response.EventProductResponse;
import com.paytogether.product.service.ProductService;
import com.paytogether.product.service.result.EventProductResult;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/products")
public class ProductController {

  private final ProductService productService;

  @GetMapping("/event")
  public List<EventProductResponse> findAllEventProduct() {
    List<EventProductResult> eventProductResultList = productService.findAllEventProducts();
    return EventProductResponse.from(eventProductResultList);
  }
}
