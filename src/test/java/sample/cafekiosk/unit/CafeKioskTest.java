package sample.cafekiosk.unit;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import sample.cafekiosk.unit.beverage.Americano;
import sample.cafekiosk.unit.beverage.Latte;
import sample.cafekiosk.unit.order.Order;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CafeKioskTest {

    @Test
    void add_manual_test() { // 수동 테스트
      CafeKiosk cafeKiosk = new CafeKiosk();
      cafeKiosk.add(new Americano());
      
      System.out.println(">>>> 담긴 음료 수 : " + cafeKiosk.getBeverages().size());
      System.out.println(">>>> 담긴 음료 : " + cafeKiosk.getBeverages().get(0).getName());
    }

    @DisplayName("음료 한 잔을 추가하면 주문 목록에 담긴다")
    @Test
    void add() { // 자동 테스트
      CafeKiosk cafeKiosk = new CafeKiosk();
      cafeKiosk.add(new Americano());
      
      assertThat(cafeKiosk.getBeverages().size()).isEqualTo(1);
      assertThat(cafeKiosk.getBeverages()).hasSize(1);
      assertThat(cafeKiosk.getBeverages().get(0).getName()).isEqualTo("아메리카노");
    }

    @Test
    void addSeveralBeverages() { // 경계값1
      CafeKiosk cafeKiosk = new CafeKiosk();
      Americano americano = new Americano();

      cafeKiosk.add(americano, 2);

      assertThat(cafeKiosk.getBeverages().get(0)).isEqualTo(americano);
      assertThat(cafeKiosk.getBeverages().get(1)).isEqualTo(americano);
      assertThat(cafeKiosk.getBeverages()).hasSize(2);
    }

    @Test
    void addZeroBeverages() { // 경계값2
      CafeKiosk cafeKiosk = new CafeKiosk();
      Americano americano = new Americano();

      assertThatThrownBy(() -> cafeKiosk.add(americano, 0))
          .isInstanceOf(IllegalArgumentException.class)
              .hasMessage("음료는 한 잔 이상 주문 가능합니다.");
    }

    @Test
    void remove() {
      CafeKiosk cafeKiosk  = new CafeKiosk();
      Americano americano = new Americano();

      cafeKiosk.add(americano);
      assertThat(cafeKiosk.getBeverages()).hasSize(1);

      cafeKiosk.remove(americano);
      assertThat(cafeKiosk.getBeverages()).isEmpty();
    }

    @Test
    void clear() {
      CafeKiosk cafeKiosk  = new CafeKiosk();
      Americano americano = new Americano();
      Latte latte = new Latte();

      cafeKiosk.add(americano);
      cafeKiosk.add(latte);
      assertThat(cafeKiosk.getBeverages()).hasSize(2);

      cafeKiosk.clear();
      assertThat(cafeKiosk.getBeverages()).isEmpty();
    }


    @DisplayName("주문 목록에 담긴 상품들의 총 금액을 계산할 수 있다.")
    @Test
    void calculateTotalPrice() {
      // given
      // 객체 세팅
      CafeKiosk cafeKiosk  = new CafeKiosk();
      Americano americano = new Americano();
      Latte latte = new Latte();

      // 행위 정의
      cafeKiosk.add(americano);
      cafeKiosk.add(latte);

      // when
      // 수행 시나리오
      int totalPrice = cafeKiosk.calculateTotalPrice();

      // then
      // 검증
      assertThat(totalPrice).isEqualTo(8500);
    }

    @Test
    void createOrder() {
      CafeKiosk cafeKiosk  = new CafeKiosk();
      Americano americano = new Americano();

      cafeKiosk.add(americano, 1);

      Order order = cafeKiosk.createOrder();

      assertThat(order.getBeverages()).hasSize(1);
      assertThat(order.getBeverages().get(0).getName()).isEqualTo("아메리카노");
    }

    @DisplayName("영업 시작 시간과 영업 종료 시간 이내에 주문을 생성할 수 있다.")
    @Test
    void createOrderWithCurrentTime() { // Test는 경계값
      CafeKiosk cafeKiosk  = new CafeKiosk();
      Americano americano = new Americano();

      cafeKiosk.add(americano, 1);

      Order order = cafeKiosk.createOrder(LocalDateTime.of(2023, 6, 13, 14, 0));

      assertThat(order.getBeverages()).hasSize(1);
      assertThat(order.getBeverages().get(0).getName()).isEqualTo("아메리카노");
    }

    @DisplayName("영업 시작 시간 이전에는 주문을 생성할 수 없다.")
    @Test
    void createOrderOutsideOpenTime() { // Test는 경계값2
      CafeKiosk cafeKiosk  = new CafeKiosk();
      Americano americano = new Americano();

      cafeKiosk.add(americano, 1);

      assertThatThrownBy(() -> cafeKiosk.createOrder(LocalDateTime.of(2023, 6, 13, 6, 0)))
          .isInstanceOf(IllegalArgumentException.class)
          .hasMessage("주문 시간이 아닙니다. 관리자에게 문의하세요.");
    }

}