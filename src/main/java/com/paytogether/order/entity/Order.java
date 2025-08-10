package com.paytogether.order.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "orders")
@Entity
public class Order {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "order_id")
  private Long id;

  private Long memberId;

  private int totalPrice;

  private LocalDateTime orderedAt;

  private Order(Long memberId, int totalPrice, LocalDateTime orderedAt) {
    this.memberId = memberId;
    this.totalPrice = totalPrice;
    this.orderedAt = orderedAt;
  }

  public static Order create(Long memberId, int totalPrice, LocalDateTime orderedAt) {
    return new Order(memberId, totalPrice, orderedAt);
  }
}

