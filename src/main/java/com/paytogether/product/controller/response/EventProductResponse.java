package com.paytogether.product.controller.response;

import com.paytogether.product.service.EventProductKeyUtil;
import com.paytogether.product.service.result.EventProductResult;
import java.util.ArrayList;
import java.util.List;

public record EventProductResponse(Long productId, int totalQuantity, int remainQuantity, Long ttlSeconds) {

  public static List<EventProductResponse> from(List<EventProductResult> eventProductResultList) {
    int length = EventProductKeyUtil.getPrefix().length();
    List<EventProductResponse> response = new ArrayList<>();
    for (EventProductResult epr : eventProductResultList) {
      Long productId = Long.parseLong(epr.key().substring(length, epr.key().length()));
      response.add(new EventProductResponse(productId, epr.totalQuantity(), epr.remainQuantity(), epr.ttlSeconds()));
    }
    return response;
  }
}
