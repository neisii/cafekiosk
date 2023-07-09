package sample.cafekiosk.spring.api.service.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sample.cafekiosk.spring.api.service.product.request.ProductCreateServiceRequest;
import sample.cafekiosk.spring.api.service.product.response.ProductResponse;
import sample.cafekiosk.spring.domain.product.Product;
import sample.cafekiosk.spring.domain.product.ProductRepository;
import sample.cafekiosk.spring.domain.product.ProductSellingStatus;

import java.util.List;
import java.util.stream.Collectors;

/**
 * readOnly = true : 읽기전용
 * CUD 에서 CUD 동작 X / only Read
 * JPA : CUD 스냅샷 저장, 변경감지 X(성능 향상)
 *
 * CQRS - Command / Read 분리 (일반적으로 2 : 8 비율)
 * Query용(R) 서비스와 Command용(CUD) 서비스를 분리할 수 있다.
 */
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ProductService {

  private final ProductRepository productRepository;
  private final ProductNumberFactory productNumberFactory;

  // 동시성 이슈 발생 위험 있음
  // 예: DB에 유니크키 제약 걸기 후 재시도 n회
  // 크리티컬한 경우 예: productNumber 같이 시퀀셜 값보다 UUID와 같은 유니크 아이디를 사용한다.
  @Transactional
  public ProductResponse createProduct(ProductCreateServiceRequest request) {
    String nextProductNumber = productNumberFactory.createNextProductNumber();

    Product product = request.toEntity(nextProductNumber);
    Product savedProduct = productRepository.save(product);

    return ProductResponse.of(savedProduct);
  }

  public List<ProductResponse> getSellingProducts() {
    List<Product> products = productRepository.findAllBySellingStatusIn(ProductSellingStatus.forDisplay());

    return products.stream()
        .map(ProductResponse::of)
        .collect(Collectors.toList());
  }
}
