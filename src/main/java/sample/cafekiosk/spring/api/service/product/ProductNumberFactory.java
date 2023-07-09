package sample.cafekiosk.spring.api.service.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import sample.cafekiosk.spring.domain.product.ProductRepository;

@RequiredArgsConstructor
@Component
public class ProductNumberFactory {

  private final ProductRepository productRepository;

  /**
   *
   * productNumber
   * 001 002 003 004
   * DB에서 마지막에 저장된 Product의 상품 번호를 읽어와서 +1
   * 009 -> 010
   */
  public String createNextProductNumber() {
    String latestProductNumber = productRepository.findLatestProductNumber();
    if (latestProductNumber == null) {
      return "001";
    }

    int latestProductNumberInt = Integer.parseInt(latestProductNumber);
    int nextProductNumberInt = latestProductNumberInt + 1;

    return String.format("%03d", nextProductNumberInt);
  }

}