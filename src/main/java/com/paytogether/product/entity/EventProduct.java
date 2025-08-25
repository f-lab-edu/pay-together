package com.paytogether.product.entity;

import com.paytogether.grouporder.entity.GroupOrderStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import java.time.LocalDateTime;

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
  private GroupOrderStatus status;
}
