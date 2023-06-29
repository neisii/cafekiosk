package sample.cafekiosk.spring.domain.product;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;


//@ActiveProfiles("test")
@DataJpaTest
class ProductTypeTest {

  @DisplayName("상품 타입이 재고 관련 타입인지 체크한다.")
  @Test
  void containsStockType() {

    // given
    ProductType givenType = ProductType.BAKERY;

    // when
    boolean result = ProductType.containsStockType(givenType);

    // then
    assertThat(result).isTrue();
  }

}