package sample.cafekiosk.spring.api.controller.product;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import sample.cafekiosk.spring.api.controller.product.dto.request.ProductCreateServiceRequest;
import sample.cafekiosk.spring.api.service.product.ProductService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static sample.cafekiosk.spring.domain.product.ProductSellingStatus.SELLING;
import static sample.cafekiosk.spring.domain.product.ProductType.HANDMADE;

@WebMvcTest(controllers = ProductController.class)//Mock 테스트를 위해 Controller Bean만
class ProductControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean // 컨테이너에 Mock으로 만든 객체를 넣어 주는 역할 Mock(ProductService)
  private ProductService productService;

  @DisplayName("신규 상품을 등록한다.")
  @Test
  void createProduct() throws Exception {

    // given
    ProductCreateServiceRequest request = ProductCreateServiceRequest.builder()
        .type(HANDMADE)
        .sellingStatus(SELLING)
        .name("아메리카노")
        .price(4000)
        .build();

    // when // then
    mockMvc.perform(
            post("/api/v1/products/new")
                .content(objectMapper.writeValueAsString(request)) // POST HTTP Serialize -> Deserialize 하므로
                .contentType(MediaType.APPLICATION_JSON)
        )
        .andDo(print())
        .andExpect(status().isOk());


  }
}