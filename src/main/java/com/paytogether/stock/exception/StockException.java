package com.paytogether.stock.exception;

import lombok.Getter;

@Getter
public class StockException extends RuntimeException{

  private final StockErrors errors;

  public StockException(StockErrors errors) {
    super(errors.getExplain());
    this.errors = errors;
  }
}
