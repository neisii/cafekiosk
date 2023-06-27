package sample.cafekiosk.spring.api.service.order;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import sample.cafekiosk.spring.api.controller.order.request.OrderCreateServiceRequest;
import sample.cafekiosk.spring.api.service.order.response.OrderResponse;
import sample.cafekiosk.spring.api.service.stock.StockRepository;
import sample.cafekiosk.spring.domain.order.OrderRepository;
import sample.cafekiosk.spring.domain.orderProduct.OrderProductRepository;
import sample.cafekiosk.spring.domain.product.Product;
import sample.cafekiosk.spring.domain.product.ProductRepository;
import sample.cafekiosk.spring.domain.product.ProductType;
import sample.cafekiosk.spring.domain.stock.Stock;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static sample.cafekiosk.spring.domain.product.ProductSellingStatus.SELLING;
import static sample.cafekiosk.spring.domain.product.ProductType.*;

@ActiveProfiles("test")
//@Transactional // tearDown과 짝이다.
@SpringBootTest // @Transactional이 주입되어 있지 않기 때문에 @DataJpaTest와 다르게 테스트 시 롤백 안된다.
//@DataJpaTest
class OrderServiceTest {

  @Autowired
  private ProductRepository productRepository;

  @Autowired
  private OrderRepository orderRepository;

  @Autowired
  private OrderProductRepository orderProductRepository;

  @Autowired
  private StockRepository stockRepository;

  @Autowired
  private OrderService orderService;

  @AfterEach
    // 매 테스트 수행 시마다 삭제 구문을 실행하여 테스트 데이터를 초기화한다.
  void tearDown() {
    orderProductRepository.deleteAllInBatch();
    productRepository.deleteAllInBatch();
//    productRepository.deleteAll();
    orderRepository.deleteAllInBatch();
    stockRepository.deleteAllInBatch();

  }

  @DisplayName("주문번호 목록을 받아 주문을 생성한다.")
  @Test
  void createOrder() {

    // given
    LocalDateTime registeredDateTime = LocalDateTime.now();

    Product product1 = createProduct(HANDMADE, "001", 1000);
    Product product2 = createProduct(HANDMADE, "002", 3000);
    Product product3 = createProduct(HANDMADE, "003", 5000);
    productRepository.saveAll(List.of(product1, product2, product3));

    OrderCreateServiceRequest request = OrderCreateServiceRequest.builder()
        .productNumbers(List.of("001", "002"))
        .build();

    // when
    OrderResponse orderResponse = orderService.createOrder(request, registeredDateTime);

    // then
    assertThat(orderResponse.getId()).isNotNull();

    assertThat(orderResponse)
        .extracting("registeredDateTime", "totalPrice")
        .contains(registeredDateTime, 4000);
    assertThat(orderResponse.getProducts()).hasSize(2)
        .extracting("productNumber", "price")
        .containsExactlyInAnyOrder(
          tuple("001", 1000),
            tuple("002", 3000)
        );

  }

  @DisplayName("중복되는 상품번호 목록으로 주문을 생성할 수 있다.")
  @Test
  void createOrderWithDuplicateProductNumbers() {

    // given
    LocalDateTime registeredDateTime = LocalDateTime.now();
    Product product1 = createProduct(HANDMADE, "001", 1000);
    Product product2 = createProduct(HANDMADE, "002", 3000);
    Product product3 = createProduct(HANDMADE, "003", 5000);
    productRepository.saveAll(List.of(product1, product2, product3));

    OrderCreateServiceRequest request = OrderCreateServiceRequest.builder()
        .productNumbers(List.of("001", "001"))
        .build();

    // when
    OrderResponse orderResponse = orderService.createOrder(request, registeredDateTime);

    // then
    assertThat(orderResponse.getId()).isNotNull();

    assertThat(orderResponse)
        .extracting("registeredDateTime", "totalPrice")
        .contains(registeredDateTime, 2000);
    assertThat(orderResponse.getProducts()).hasSize(2)
        .extracting("productNumber", "price")
        .containsExactlyInAnyOrder(
            tuple("001", 1000),
            tuple("001", 1000)
        );

  }

  @DisplayName("재고와 관련된 상품이 포함되어 있는 주문번호 목록을 받아 주문을 생성한다.")
  @Test
  void createOrderWithStock() {

    // given
    LocalDateTime registeredDateTime = LocalDateTime.now();

    Product product1 = createProduct(BOTTLE, "001", 1000);
    Product product2 = createProduct(BAKERY, "002", 3000);
    Product product3 = createProduct(HANDMADE, "003", 5000);
    productRepository.saveAll(List.of(product1, product2, product3));


    Stock stock1 = Stock.create("001", 2);
    Stock stock2 = Stock.create("002", 2);
    stockRepository.saveAll(List.of(stock1, stock2));

    OrderCreateServiceRequest request = OrderCreateServiceRequest.builder()
            .productNumbers(List.of("001","001","002","003"))
            .build();

    // when
    OrderResponse orderResponse = orderService.createOrder(request, registeredDateTime);

    // then
    assertThat(orderResponse.getId()).isNotNull();

    assertThat(orderResponse)
            .extracting("registeredDateTime", "totalPrice")
            .contains(registeredDateTime, 10000);
    assertThat(orderResponse.getProducts()).hasSize(4)
            .extracting("productNumber", "price")
            .containsExactlyInAnyOrder(
                    tuple("001", 1000),
                    tuple("001", 1000),
                    tuple("002", 3000),
                    tuple("003", 5000)
            );

    List<Stock> stocks = stockRepository.findAll();
    assertThat(stocks).hasSize(2)
            .extracting("productNumber", "quantity")
            .containsExactlyInAnyOrder(
                    tuple("001", 0),
                    tuple("002", 1)
            );
  }

  @DisplayName("재고가 부족한 상품으로 주문을 생성하려는 경우 예외가 발생한다.")
  @Test
  void createOrderWithNoStock() {

    // given
    LocalDateTime registeredDateTime = LocalDateTime.now();

    Product product1 = createProduct(BOTTLE, "001", 1000);
    Product product2 = createProduct(BAKERY, "002", 3000);
    Product product3 = createProduct(HANDMADE, "003", 5000);
    productRepository.saveAll(List.of(product1, product2, product3));


    // 좋은 예
//    Stock stock1 = Stock.create("001", 1);
//    Stock stock2 = Stock.create("002", 1);

    // 나쁜 예
    Stock stock1 = Stock.create("001", 2);
    Stock stock2 = Stock.create("002", 2);
    stock1.deductQuantity(1); // todo: 이렇게 작성하면 안된다.
    stockRepository.saveAll(List.of(stock1, stock2));

    OrderCreateServiceRequest request = OrderCreateServiceRequest.builder()
            .productNumbers(List.of("001","001","002","003"))
            .build();

    // when // then
    assertThatThrownBy(() -> orderService.createOrder(request, registeredDateTime))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("재고가 부족한 상품이 있습니다.");
  }


  private Product createProduct(ProductType type, String productNumber, int price) {
    return Product.builder()
        .type(type)
        .productNumber(productNumber)
        .price(price)
        .sellingStatus(SELLING)
        .name("메뉴명")
        .build();
  }

}