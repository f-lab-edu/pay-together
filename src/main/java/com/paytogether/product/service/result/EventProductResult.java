package com.paytogether.product.service.result;

public record EventProductResult(String key, int totalQuantity, int remainQuantity,
                                 long ttlSeconds) {

}
