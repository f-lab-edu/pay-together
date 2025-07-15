package com.paytogether.stock.entity;

import com.paytogether.stock.exception.StockErrors;
import com.paytogether.stock.exception.StockException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
public class Stock {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "stock_id")
  private Long id;

  @Column(nullable = false)
  private Long productId;

  @Min(value = 0)
  private int quantity;

  @Column(nullable = false)
  private LocalDateTime createdAt;

  @Column(nullable = false)
  private LocalDateTime modifiedAt;

  private Stock(Long productId, int quantity, LocalDateTime createdAt) {
    this.productId = productId;
    this.quantity = quantity;
    this.createdAt = createdAt;
    this.modifiedAt = createdAt;
  }

  public static Stock create(Long productId, int quantity, LocalDateTime now) {
    return new Stock(productId, quantity, now);
  }

  public void decreaseQuantity(int quantity) {
    if (quantityLessThan(quantity)) {
      throw new StockException(StockErrors.NOT_ENOUGH_QUANTITY);
    }
    this.quantity -= quantity;
  }

  public void modifyTime(LocalDateTime modifiedAt) {
    this.modifiedAt = modifiedAt;
  }

  public boolean quantityLessThan(int quantity) {
    return this.quantity < quantity;
  }
}
