package com.paytogether.product.entity;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ProductType {
  HOME_APPLIANCES("가전제품"),
  FOOD("식품"),
  HOUSEHOLD_GOODS("생활용품");

  private final String explain;
}
