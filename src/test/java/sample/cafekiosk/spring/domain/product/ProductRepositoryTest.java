package sample.cafekiosk.spring.domain.product;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;
import static sample.cafekiosk.spring.domain.product.ProductSellingStatus.*;
import static sample.cafekiosk.spring.domain.product.ProductType.HANDMADE;

//@SpringBootTest // Spring Server 를 띄워서 테스트 한다. (권장)
@ActiveProfiles("test") // 사용할 프로필 지정
//@SpringBootTest
@DataJpaTest // JPA 관련 Bean만 주입하여 SpringBootTest 보다 가벼워 좀 더 빠르다. //
class ProductRepositoryTest {

  @Autowired
  private ProductRepository productRepository;

  @DisplayName("원하는 판매상태를 가진 상품들을 조회한다.")
  @Test
  void findAllBySellingStatusIn() {

    // given
    Product product1 = createProduct("001", HANDMADE, SELLING, "아메리카노", 4000);
    Product product2 = createProduct("002", HANDMADE, HOLD, "카페라떼", 4500);
    Product product3 = createProduct("003", HANDMADE, STOP_SELLING, "팥빙수", 7000);

    productRepository.saveAll(List.of(product1, product2, product3));

    // when
    List<Product> products = productRepository.findAllBySellingStatusIn(List.of(SELLING, HOLD));


    // then
    assertThat(products).hasSize(2)
        .extracting("productNumber", "name", "sellingStatus")
        .containsExactlyInAnyOrder( // 순서 무관하게 확인한다. (InAnyOrder)
            tuple("001", "아메리카노", SELLING),
            tuple("002", "카페라떼", HOLD)
        );

  }

  @DisplayName("상품번호 목록으로 상품들을 조회한다.")
  @Test
  void findAllByProductNumberIn() {

    // given
    Product product1 = createProduct("001", HANDMADE, SELLING, "아메리카노", 4000);
    Product product2 = createProduct("002", HANDMADE, HOLD, "카페라떼", 4500);
    Product product3 = createProduct("003", HANDMADE, STOP_SELLING, "팥빙수", 7000);

    productRepository.saveAll(List.of(product1, product2, product3));

    // when
    List<Product> products = productRepository.findAllByProductNumberIn(List.of("001", "002"));


    // then
    assertThat(products).hasSize(2)
        .extracting("productNumber", "name", "sellingStatus")
        .containsExactlyInAnyOrder(
            tuple("001", "아메리카노", SELLING),
            tuple("002", "카페라떼", HOLD)
        );
  }

  @DisplayName("가장 마지막으로 저장한 상품의 상품번호를 가져온다.")
  @Test
  void findLatestProductNumber() {

    // given
    String targetProductNumber = "003";

    Product product1 = createProduct("001", HANDMADE, SELLING, "아메리카노", 4000);
    Product product2 = createProduct("002", HANDMADE, HOLD, "카페라떼", 4500);
    Product product3 = createProduct(targetProductNumber, HANDMADE, STOP_SELLING, "팥빙수", 7000);
    productRepository.saveAll(List.of(product1, product2, product3));

    // when
    String productNumber = productRepository.findLatestProductNumber();


    // then
    assertThat(productNumber).isEqualTo(targetProductNumber);
  }

  @DisplayName("가장 마지막으로 저장한 상품의 상품번호를 읽어올 때, 상품이 하나도 없는 경우에는 null을 반환한다.")
  @Test
  void findLatestProductNumberWhenProductIsEmpty() {

    // given
    // when
    String productNumber = productRepository.findLatestProductNumber();

    // then
    assertThat(productNumber).isNull();
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