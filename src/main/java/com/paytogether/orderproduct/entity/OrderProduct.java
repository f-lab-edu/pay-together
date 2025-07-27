package com.paytogether.orderproduct.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class OrderProduct {


  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "order_product_id")
  private Long id;

  private Long orderId;

  private Long productId;

  private int quantity;

  private int totalPrice;

  private OrderProduct(Long orderId, Long productId, int quantity, int totalPrice) {
    this.orderId = orderId;
    this.productId = productId;
    this.quantity = quantity;
    this.totalPrice = totalPrice;
  }

  public static OrderProduct create(Long orderId, Long productId, int quantity, int totalPrice) {
    return new OrderProduct(orderId, productId, quantity, totalPrice);
  }
}
