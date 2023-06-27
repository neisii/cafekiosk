package sample.cafekiosk.spring.domain.order;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sample.cafekiosk.spring.domain.BaseEntity;
import sample.cafekiosk.spring.domain.orderProduct.OrderProduct;
import sample.cafekiosk.spring.domain.product.Product;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "orders") // Database 에서 Order 는 키워드이므로 테이블 이름을 새로 지정한다.
@Entity
public class Order extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Enumerated(EnumType.STRING)
  private OrderStatus orderStatus;

  private int totalPrice;

  private LocalDateTime registeredDateTime; // 주문을 완료한 시간


  @OneToMany(mappedBy = "order", cascade = CascadeType.ALL) // order에 변경사항이 생기면 같이 변경되도록
  private List<OrderProduct> orderProducts = new ArrayList<>();

  public Order(List<Product> products, LocalDateTime registeredDateTime) {
    this.orderStatus = OrderStatus.INIT;
    this.totalPrice = calculateTotalPrice(products);
    this.registeredDateTime = registeredDateTime;
    this.orderProducts = products.stream()
        .map(product -> new OrderProduct(this, product))
        .collect(Collectors.toList());
  }

  public static Order create(List<Product> products, LocalDateTime registeredDateTime) {
    return new Order(products, registeredDateTime);
  }

  private static int calculateTotalPrice(List<Product> products) {
    return products.stream()
        .mapToInt(Product::getPrice)
        .sum();
  }

}
