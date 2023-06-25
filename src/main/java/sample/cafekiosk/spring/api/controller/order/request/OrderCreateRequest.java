package sample.cafekiosk.spring.api.controller.order.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor // @RequestBody 생성자 기반으로 객체 생성하기 위함
public class OrderCreateRequest {

  private List<String> productNumbers;

  @Builder
  private OrderCreateRequest(List<String> productNumbers) {
    this.productNumbers = productNumbers;
  }

}
