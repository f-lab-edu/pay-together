package com.paytogether.product.service.command;

import com.paytogether.product.entity.EventProduct;
import com.paytogether.product.entity.EventProductStatus;
import com.paytogether.product.entity.ProductSellingStatus;
import com.paytogether.product.entity.ProductType;
import java.time.LocalDateTime;

public record CreateEventProductCommand(ProductType productType,
                                        ProductSellingStatus productSellingStatus, String name,
                                        int price, int eventPrice, LocalDateTime startEventTime,
                                        LocalDateTime endEventTime,
                                        EventProductStatus eventProductStatus) {

  public EventProduct toEntity(LocalDateTime createdAt) {
    return new EventProduct(productType, productSellingStatus, name, price, createdAt, eventPrice,
        startEventTime, endEventTime, eventProductStatus);
  }

}
