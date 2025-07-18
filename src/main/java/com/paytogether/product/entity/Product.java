package com.paytogether.product.entity;

import com.paytogether.product.exception.ProductErrors;
import com.paytogether.product.exception.ProductException;
import com.paytogether.stock.exception.StockErrors;
import com.paytogether.stock.exception.StockException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

  @Min(value = 0)
  private int quantity;

  @Column(nullable = false)
  private LocalDateTime createdAt;

  private Product(ProductType type, ProductSellingStatus sellingStatus, String name, int price,
      int quantity,
      LocalDateTime createdAt) {
    this.type = type;
    this.sellingStatus = sellingStatus;
    this.name = name;
    this.price = price;
    this.quantity = quantity;
    this.createdAt = createdAt;
  }

  public static Product create(ProductType type, ProductSellingStatus sellingStatus, String name,
      int price, int quantity, LocalDateTime createdAt) {
    return new Product(type, sellingStatus, name, price, quantity, createdAt);
  }

  public void decreaseQuantity(int quantity) {
    if (quantityLessThan(quantity)) {
      throw new ProductException(ProductErrors.NOT_ENOUGH_QUANTITY);
    }
    this.quantity -= quantity;
  }

  public boolean quantityLessThan(int quantity) {
    return this.quantity < quantity;
  }
}
