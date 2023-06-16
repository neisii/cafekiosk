package sample.cafekiosk.spring.domain.orderProduct;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sample.cafekiosk.spring.domain.BaseEntity;
import sample.cafekiosk.spring.domain.order.Order;
import sample.cafekiosk.spring.domain.product.Product;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class OrderProduct extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY) // 지연로딩으로 꼭 필요한 곳에 사용하도록 하기 위함
  private Order order;

  @ManyToOne(fetch = FetchType.LAZY) // 지연로딩으로 꼭 필요한 곳에 사용하도록 하기 위함
  private Product product;

  public OrderProduct(Order order, Product product) {
    this.order = order;
    this.product = product;
  }

}
