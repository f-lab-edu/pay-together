package com.paytogether.order.service;

import static org.assertj.core.api.Assertions.tuple;

import com.paytogether.auth.service.HttpSessionManager;
import com.paytogether.auth.service.LoginService;
import com.paytogether.member.entity.Gender;
import com.paytogether.member.entity.Member;
import com.paytogether.member.repository.MemberRepository;
import com.paytogether.order.repository.OrderRepository;
import com.paytogether.order.service.command.OrderCreateCommand;
import com.paytogether.order.service.result.OrderCreateResult;
import com.paytogether.orderproduct.repository.OrderProductRepository;
import com.paytogether.product.entity.Product;
import com.paytogether.product.entity.ProductSellingStatus;
import com.paytogether.product.entity.ProductType;
import com.paytogether.product.repository.ProductRepository;
import com.paytogether.stock.entity.Stock;
import com.paytogether.stock.repository.StockRepository;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class OrderServiceTest {

  @Autowired
  private OrderService orderService;

  @Autowired
  private OrderRepository orderRepository;

  @Autowired
  private OrderProductRepository orderProductRepository;

  @Autowired
  private StockRepository stockRepository;

  @Autowired
  private ProductRepository productRepository;

  @Autowired
  private HttpSessionManager httpSessionManager;

  @Autowired
  private LoginService loginService;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private MemberRepository memberRepository;

  private Long productId1;
  private Long productId2;

  @BeforeEach
  void before() {
    String password = "1234!678";
    String email = "pay@spring.com";
    Member member = Member.createMember(email, passwordEncoder.encode(password), "name", 100,
        Gender.MALE,
        "address", "01012345678", LocalDateTime.now());
    memberRepository.save(member);
    loginService.login(email, password);
  }

  @DisplayName("주문 성공 테스트")
  @Test
  void test() {
    // given
    int productPrice1 = 1000;
    int productPrice2 = 5000;
    Product product1 = Product.create(ProductType.FOOD, ProductSellingStatus.SELLING, "fish", productPrice1,
        LocalDateTime.now());
    Product product2 = Product.create(ProductType.HOME_APPLIANCES, ProductSellingStatus.SELLING, "tv", productPrice2,
        LocalDateTime.now());
    Product saveProduct1 = productRepository.save(product1);
    Product saveProduct2 = productRepository.save(product2);
    productId1 = saveProduct1.getId();
    productId2 = saveProduct2.getId();

    int productQuantity1 = 10;
    int productQuantity2 = 20;
    Stock stock1 = Stock.create(productId1, productQuantity1, LocalDateTime.now());
    Stock stock2 = Stock.create(productId2, productQuantity2, LocalDateTime.now());
    stockRepository.saveAll(List.of(stock1, stock2));

    Map<Long, Integer> productQuantityMap = new HashMap<>();
    int orderProductQuantity1 = 5;
    int orderProductQuantity2 = 10;
    productQuantityMap.put(productId1, orderProductQuantity1);
    productQuantityMap.put(productId2, orderProductQuantity2);
    OrderCreateCommand command = new OrderCreateCommand(productQuantityMap);
    // when
    OrderCreateResult result = orderService.order(command, LocalDateTime.now());
    // then
    SoftAssertions softAssertions = new SoftAssertions();
    softAssertions.assertThat(result.orderTotalPrice()).isEqualTo(5000 + 50000);
    softAssertions.assertThat(result.orderProductResults()).hasSize(2);

    List<Stock> stocks = stockRepository.findAllByProductIdIn(List.of(1L, 2L));
    int expectedStock1 = productQuantity1 - orderProductQuantity1;
    int expectedStock2 = productQuantity2 - orderProductQuantity2;
    softAssertions.assertThat(stocks)
        .extracting("productId", "quantity")
        .containsExactlyInAnyOrder(
            tuple(productId1, expectedStock1), // 10 - 5
            tuple(productId2, expectedStock2) // 20 - 10
        );
  }
}
