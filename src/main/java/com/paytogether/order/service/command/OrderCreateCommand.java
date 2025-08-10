package com.paytogether.order.service.command;

import java.util.List;

public record OrderCreateCommand(List<ProductQuantity> prductQuantityList) {

}
