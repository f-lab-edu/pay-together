package com.paytogether.product.service.result;

import com.paytogether.product.entity.EventProduct;
import com.paytogether.product.entity.EventProductStatus;
import com.paytogether.product.entity.ProductSellingStatus;
import com.paytogether.product.entity.ProductType;
import java.time.LocalDateTime;

public record CreateEventProductResult(ProductType productType,
                                       ProductSellingStatus productSellingStatus, String name,
                                       int price, int eventPrice, LocalDateTime startEventTime,
                                       LocalDateTime endEventTime,
                                       EventProductStatus eventProductStatus) {

  public static CreateEventProductResult from(EventProduct eventProduct) {
    return new CreateEventProductResult(eventProduct.getType(), eventProduct.getSellingStatus(),
        eventProduct.getName(), eventProduct.getPrice(), eventProduct.getEventPrice(),
        eventProduct.getStartEventTime(), eventProduct.getEndEventTime(), eventProduct.getStatus());
  }
}
