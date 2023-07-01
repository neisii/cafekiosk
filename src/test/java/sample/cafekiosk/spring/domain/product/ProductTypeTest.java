package sample.cafekiosk.spring.domain.product;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import sample.cafekiosk.spring.IntergrationTestSupport;

import static org.assertj.core.api.Assertions.assertThat;


//@ActiveProfiles("test")
//@DataJpaTest
class ProductTypeTest extends IntergrationTestSupport {

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

  @CsvSource({"HANDMADE,false","BOTTLE,true","BAKERY,true"})
  @DisplayName("상품 타입이 재고 관련 타입인지 체크한다.")
  @ParameterizedTest
  void containsStockType(ProductType givenType, boolean expected) {

    // when
    boolean result = ProductType.containsStockType(givenType);

    // then
    assertThat(result).isEqualTo(expected);
  }

}