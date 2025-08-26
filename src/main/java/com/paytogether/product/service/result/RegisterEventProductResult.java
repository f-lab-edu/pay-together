package com.paytogether.product.service.result;

public record RegisterEventProductResult(String key, int totalQuantity, int remainQuantity, long ttlSeconds) {

}
