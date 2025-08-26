package com.paytogether.product.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class EventProduct extends Product {

  @Column(nullable = false)
  private int eventPrice;

  @Column(nullable = false)
  private LocalDateTime startEventTime;

  @Column(nullable = false)
  private LocalDateTime endEventTime;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private EventProductStatus status;

  public EventProduct(ProductType productType, ProductSellingStatus productSellingStatus,
      String name, int price, LocalDateTime createdAt, int eventPrice, LocalDateTime startEventTime,
      LocalDateTime endEventTime, EventProductStatus status) {
    super(productType, productSellingStatus, name, price, createdAt);
    this.eventPrice = eventPrice;
    this.startEventTime = startEventTime;
    this.endEventTime = endEventTime;
    this.status = status;
  }
}
