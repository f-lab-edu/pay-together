package com.paytogether.order.service;

import com.paytogether.auth.service.HttpSessionManager;
import com.paytogether.order.entity.Order;
import com.paytogether.order.repository.OrderRepository;
import com.paytogether.order.service.command.OrderCreateCommand;
import com.paytogether.order.service.result.OrderCreateResult;
import com.paytogether.order.service.result.OrderProductResult;
import com.paytogether.orderproduct.entity.OrderProduct;
import com.paytogether.orderproduct.repository.OrderProductRepository;
import com.paytogether.product.entity.Product;
import com.paytogether.product.repository.ProductRepository;
import com.paytogether.stock.entity.Stock;
import com.paytogether.stock.repository.StockRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class OrderService {

  private final HttpSessionManager httpSessionManager;
  private final ProductRepository productRepository;
  private final StockRepository stockRepository;
  private final OrderRepository orderRepository;
  private final OrderProductRepository orderProductRepository;

  @Transactional
  public OrderCreateResult order(OrderCreateCommand command, LocalDateTime orderedAt) {
    Map<Long, Integer> productQuantityMap = command.productQuantityMap();

    List<Long> productIds = new ArrayList<>(productQuantityMap.keySet());

    List<Stock> stocks = stockRepository.findAllByProductIdIn(productIds);
    List<Long> canOrderProducts = extractCanOrderProduct(productQuantityMap, stocks);

    List<Product> products = productRepository.findAllById(canOrderProducts);
    Map<Long, Integer> productTotalPriceMap = new HashMap<>();
    int orderTotalPrice = 0;
    orderTotalPrice = getOrderTotalPrice(productQuantityMap, products, productTotalPriceMap,
        orderTotalPrice);

    Long memberId = httpSessionManager.getMemberId();

    Order order = Order.create(memberId, orderTotalPrice, orderedAt);
    Order savedOrder = orderRepository.save(order);
    Long orderId = savedOrder.getId();

    List<OrderProduct> orderProducts = getOrderProducts(
        productQuantityMap, productTotalPriceMap, orderId);
    orderProductRepository.saveAll(orderProducts);

    List<OrderProductResult> result = OrderProductResult.from(orderProducts);
    return OrderCreateResult.of(orderTotalPrice, result);
  }

  private List<OrderProduct> getOrderProducts(Map<Long, Integer> productQuantityMap,
      Map<Long, Integer> productTotalPriceMap, Long orderId) {
    List<OrderProduct> orderProducts = new ArrayList<>();
    for (Map.Entry<Long, Integer> entry : productTotalPriceMap.entrySet()) {
      Long productId = entry.getKey();
      int quantity = productQuantityMap.get(productId);
      int totalPrice = entry.getValue();
      OrderProduct orderProduct = OrderProduct.create(orderId, productId, quantity, totalPrice);
      orderProducts.add(orderProduct);
    }
    return orderProducts;
  }

  private int getOrderTotalPrice(Map<Long, Integer> productQuantityMap,
      List<Product> products, Map<Long, Integer> productTotalPriceMap, int orderTotalPrice) {
    for (Product product : products) {
      Long productId = product.getId();
      int quantity = productQuantityMap.get(productId).intValue();
      int productTotalPrice = quantity * product.getPrice();
      productTotalPriceMap.put(productId, productTotalPrice);
      orderTotalPrice += productTotalPrice;
    }
    return orderTotalPrice;
  }

  private List<Long> extractCanOrderProduct(Map<Long, Integer> productQuantityMap,
      List<Stock> stocks) {
    List<Long> canOrderProducts = new ArrayList<>();
    for (Stock stock : stocks) {
      Long productId = stock.getProductId();
      int quantity = productQuantityMap.get(productId).intValue();
      stock.decreaseQuantity(quantity);
      canOrderProducts.add(productId);
    }
    return canOrderProducts;
  }
}
