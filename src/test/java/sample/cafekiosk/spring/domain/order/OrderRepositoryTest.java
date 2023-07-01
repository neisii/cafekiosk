package sample.cafekiosk.spring.domain.order;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import sample.cafekiosk.spring.IntergrationTestSupport;
import sample.cafekiosk.spring.domain.orderProduct.OrderProductRepository;
import sample.cafekiosk.spring.domain.product.Product;
import sample.cafekiosk.spring.domain.product.ProductRepository;
import sample.cafekiosk.spring.domain.product.ProductSellingStatus;
import sample.cafekiosk.spring.domain.product.ProductType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static sample.cafekiosk.spring.domain.product.ProductSellingStatus.*;
import static sample.cafekiosk.spring.domain.product.ProductType.HANDMADE;

//@SpringBootTest
class OrderRepositoryTest extends IntergrationTestSupport {

  @Autowired
  private ProductRepository productRepository;

  @Autowired
  private OrderProductRepository orderProductRepository;

  @Autowired
  private OrderRepository orderRepository;

  @AfterEach
  void tearDown() {
    orderProductRepository.deleteAllInBatch();
    productRepository.deleteAllInBatch();
    orderRepository.deleteAllInBatch();
  }

  @DisplayName("주문 완료 시간과 주문 상태로 주문 내역을 찾는다. 주문 완료 시간과 주문 상태를 입력하지 않는 경우 예외가 발생 한다.")
  @Test
  void findOrdersBy() {
    //given
    LocalDateTime registerDateTime = LocalDateTime.now();
    LocalDate orderDate = LocalDateTime.now().toLocalDate();
    Product product1 = createProduct("001", HANDMADE, SELLING, "아메리카노", 4000);
    Product product2 = createProduct("002", HANDMADE, HOLD, "카페라떼", 4500);
    Product product3 = createProduct("003", HANDMADE, STOP_SELLING, "팥빙수", 7000);

    List<Product> products = List.of(product1, product2, product3);
    productRepository.saveAll(products);

    Order order = Order.builder()
        .products(products)
        .orderStatus(OrderStatus.PAYMENT_COMPLETED)
        .registeredDateTime(registerDateTime)
        .build();
    orderRepository.saveAll(List.of(order));

    // when
    List<Order> orders = orderRepository.findOrdersBy(orderDate.atStartOfDay(), orderDate.plusDays(1).atStartOfDay(), OrderStatus.PAYMENT_COMPLETED);

    // then
    Assertions.assertThat(orders).hasSize(1);
  }

  private Product createProduct(String number, ProductType type, ProductSellingStatus sellingStatus, String name, int price) {
    return Product.builder()
        .productNumber(number)
        .type(type)
        .sellingStatus(sellingStatus)
        .name(name)
        .price(price)
        .build();
  }

}