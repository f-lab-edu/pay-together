package com.paytogether.product.controller.request;

public record RegisterEventProductRequest(Long productId, int totalQuantity, Long ttlSeconds) {

}
