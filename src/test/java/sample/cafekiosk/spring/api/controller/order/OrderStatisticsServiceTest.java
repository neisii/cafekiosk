package sample.cafekiosk.spring.api.controller.order;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import sample.cafekiosk.spring.client.mail.MailSendClient;
import sample.cafekiosk.spring.domain.history.mail.MailSendHistory;
import sample.cafekiosk.spring.domain.history.mail.MailSendHistoryRepository;
import sample.cafekiosk.spring.domain.order.Order;
import sample.cafekiosk.spring.domain.order.OrderRepository;
import sample.cafekiosk.spring.domain.order.OrderStatus;
import sample.cafekiosk.spring.domain.orderProduct.OrderProductRepository;
import sample.cafekiosk.spring.domain.product.Product;
import sample.cafekiosk.spring.domain.product.ProductRepository;
import sample.cafekiosk.spring.domain.product.ProductType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static sample.cafekiosk.spring.domain.product.ProductSellingStatus.SELLING;
import static sample.cafekiosk.spring.domain.product.ProductType.HANDMADE;

// 메일 전송 같이 긴 네트워크를 타는 등의 지연되는 로직에서는 @Transactional 을 걸지 않는 게 좋다.
@SpringBootTest
class OrderStatisticsServiceTest {

  @Autowired
  private OrderStatisticsService orderStatisticsService;

  @Autowired
  private OrderProductRepository orderProductRepository;

  @Autowired
  private OrderRepository orderRepository;

  @Autowired
  private ProductRepository productRepository;

  @Autowired
  private MailSendHistoryRepository mailSendHistoryRepository;

  @MockBean
  private MailSendClient mailSendClient;

  @AfterEach
  void tearDown() {
    orderProductRepository.deleteAllInBatch();
    orderRepository.deleteAllInBatch();
    productRepository.deleteAllInBatch();
    mailSendHistoryRepository.deleteAllInBatch();
  }

  @DisplayName("결제완료 주문들을 조회하여 매출 통계 메일을 전송한다.")
  @Test
  void sendOrderStatisticsMail() {

    // given
    LocalDateTime registeredDateTime = LocalDateTime.of(2023, 3, 5, 0, 0);

    Product product1 = createProduct(HANDMADE, "001", 1000);
    Product product2 = createProduct(HANDMADE, "002", 2000);
    Product product3 = createProduct(HANDMADE, "003", 3000);
    List<Product> products = List.of(product1, product2, product3);
    productRepository.saveAll(products);

    Order order1 = createPaymentCompletedOrder(LocalDateTime.of(2023, 3, 4, 23, 59, 59), products);
    Order order2 = createPaymentCompletedOrder(registeredDateTime, products);
    Order order3 = createPaymentCompletedOrder(LocalDateTime.of(2023, 3, 5, 23, 59, 59), products);
    Order order4 = createPaymentCompletedOrder(LocalDateTime.of(2023, 3, 6, 0, 0 ), products);
     List<Order> orders = List.of(order1, order2, order3, order4);
    orderRepository.saveAll(orders);

    // 무슨 값이 들어올진 모르겠지만 일단 이 타입으로 들어오는지 검증
    // stubbing 한다. : Mock 객체의 행위 정의
    when(mailSendClient.sendMail(any(String.class), any(String.class), any(String.class), any(String.class)))
    .thenReturn(true);

    // when
    boolean result = orderStatisticsService.sendOrderStatisticsMail(LocalDate.of(2023, 3, 5), "test@test.com");

    // then
    assertThat(result).isTrue();

    List<MailSendHistory> histories = mailSendHistoryRepository.findAll();
    assertThat(histories).hasSize(1)
        .extracting("content")
        .contains("총 매출 합계는 12000원 입니다.");
  }

  private Order createPaymentCompletedOrder(LocalDateTime registeredDateTime, List<Product> products) {
    Order order = Order.builder()
        .products(products)
        .orderStatus(OrderStatus.PAYMENT_COMPLETED)
        .registeredDateTime(registeredDateTime)
        .build();
    return orderRepository.save(order);
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