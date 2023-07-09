package sample.cafekiosk.spring.api.controller.product;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import sample.cafekiosk.spring.api.ApiResponse;
import sample.cafekiosk.spring.api.controller.product.dto.request.ProductCreateRequest;
import sample.cafekiosk.spring.api.service.product.ProductService;
import sample.cafekiosk.spring.api.service.product.response.ProductResponse;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor // 생성자를 만들어준다.
@RestController
public class ProductController {

  private final ProductService productService;

  @PostMapping("/api/v1/products/new")
  public ApiResponse<ProductResponse> createProduct(@Valid @RequestBody ProductCreateRequest request) { // @Valid가 있어야 request 검증 수행
    return ApiResponse.ok(productService.createProduct(request.toServiceRequest()));
  }

  @GetMapping("/api/v1/products/selling")
  public ApiResponse<List<ProductResponse>> getSellingProducts() {
    return ApiResponse.ok(productService.getSellingProducts());
  }


}
