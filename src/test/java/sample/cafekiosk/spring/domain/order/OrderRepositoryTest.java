package sample.cafekiosk.spring.domain.order;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import sample.cafekiosk.spring.domain.product.Product;
import sample.cafekiosk.spring.domain.product.ProductSellingStatus;
import sample.cafekiosk.spring.domain.product.ProductType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static sample.cafekiosk.spring.domain.product.ProductSellingStatus.*;
import static sample.cafekiosk.spring.domain.product.ProductType.HANDMADE;

//@ActiveProfiles("test")
//@SpringBootTest
@DataJpaTest
class OrderRepositoryTest {

  @Autowired
  private OrderRepository orderRepository;

  @DisplayName("주문 완료 시간과 주문 상태로 주문 내역을 찾는다. 주문 완료 시간과 주문 상태를 입력하지 않는 경우 예외가 발생 한다.")
  @Test
  void findOrdersBy() {
    //given
    LocalDateTime registerDateTime = LocalDateTime.now();
    LocalDate orderDate = LocalDateTime.now().toLocalDate();
    Product product1 = createProduct("001", HANDMADE, SELLING, "아메리카노", 4000);
    Product product2 = createProduct("002", HANDMADE, HOLD, "카페라떼", 4500);
    Product product3 = createProduct("003", HANDMADE, STOP_SELLING, "팥빙수", 7000);

    Order order1 = Order.create(List.of(product1, product2, product3), registerDateTime);
    orderRepository.saveAll(List.of(order1));

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