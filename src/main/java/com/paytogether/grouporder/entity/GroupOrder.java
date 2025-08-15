package com.paytogether.grouporder.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class GroupOrder {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "group_order_id")
  private Long id;

  @Column(nullable = false)
  private Long productId;

  @Column(nullable = false)
  private int targetQuantity;

  @Column(nullable = false)
  private int currentQuantity;

  @Column(nullable = false)
  private int price;

  @Column(nullable = false)
  private LocalDateTime startTime;

  @Column(nullable = false)
  private LocalDateTime endTime;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private GroupOrderStatus status;

  @Column(nullable = false)
  private boolean sellPolicyAfterDrop;
}
