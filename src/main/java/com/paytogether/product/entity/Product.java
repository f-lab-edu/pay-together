package com.paytogether.product.entity;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn
@Entity
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "product_id")
  private Long id;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private ProductType type;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private ProductSellingStatus sellingStatus;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private int price;

  @Column(nullable = false)
  private LocalDateTime createdAt;

  private Product(ProductType type, ProductSellingStatus sellingStatus, String name, int price,
      LocalDateTime createdAt) {
    this.type = type;
    this.sellingStatus = sellingStatus;
    this.name = name;
    this.price = price;
    this.createdAt = createdAt;
  }

  public static Product create(ProductType type, ProductSellingStatus sellingStatus, String name,
      int price, LocalDateTime createdAt) {
    return new Product(type, sellingStatus, name, price, createdAt);
  }
}
