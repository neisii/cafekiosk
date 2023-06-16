package sample.cafekiosk.spring.api.service.product.response;

import lombok.Builder;
import lombok.Getter;
import sample.cafekiosk.spring.domain.product.Product;
import sample.cafekiosk.spring.domain.product.ProductSellingStatus;
import sample.cafekiosk.spring.domain.product.ProductType;

@Getter // JSON 형태로 응답을 내려줄 수 있음
public class ProductResponse {

  private Long id;
  private String productNumber;
  private ProductType type;
  private ProductSellingStatus sellingStatus;
  private String name;
  private int price;

  @Builder // Builder는 private 로 선언하여 생성자를 직접 사용할 수 없도록 선언한다.
  private ProductResponse(Long id, String productNumber, ProductType type, ProductSellingStatus sellingStatus, String name, int price) {
    this.id = id;
    this.productNumber = productNumber;
    this.type = type;
    this.sellingStatus = sellingStatus;
    this.name = name;
    this.price = price;
  }

  // Product Entity convert to ProductReponse Object
  public static ProductResponse of(Product product) {
    return ProductResponse.builder()
        .id(product.getId())
        .productNumber(product.getProductNumber())
        .type(product.getType())
        .sellingStatus(product.getSellingStatus())
        .name(product.getName())
        .price(product.getPrice())
        .build();
  }
}
