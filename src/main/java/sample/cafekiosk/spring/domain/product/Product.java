package sample.cafekiosk.spring.domain.product;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sample.cafekiosk.spring.api.service.product.request.ProductCreateServiceRequest;
import sample.cafekiosk.spring.domain.BaseEntity;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Product extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id; // 채번 역할

  private String productNumber; // 001 002 003

  @Enumerated(EnumType.STRING)
  private ProductType type;

  @Enumerated(EnumType.STRING)
  private ProductSellingStatus sellingStatus;

  private String name;

  private int price;

  @Builder
  private Product(String productNumber, ProductType type, ProductSellingStatus sellingStatus, String name, int price) {
    this.productNumber = productNumber;
    this.type = type;
    this.sellingStatus = sellingStatus;
    this.name = name;
    this.price = price;
  }

}
