package com.paytogether.product.service;

public final class EventProductKeyUtil {

  private static final String PREFIX = "event:product:";

  private EventProductKeyUtil() {
  }

  public static String getProductKey(long productId) {
    return PREFIX + productId;
  }

  public static String getAllKey() {
    return PREFIX + "*";
  }

  public static String getPrefix() {
    return PREFIX;
  }
}
