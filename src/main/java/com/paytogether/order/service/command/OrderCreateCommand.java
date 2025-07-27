package com.paytogether.order.service.command;

import java.util.Map;

public record OrderCreateCommand(Map<Long, Integer> productQuantityMap) {

}
