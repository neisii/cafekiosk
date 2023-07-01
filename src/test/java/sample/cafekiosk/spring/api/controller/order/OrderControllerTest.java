package sample.cafekiosk.spring.api.controller.order;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import sample.cafekiosk.spring.ControllerTestSupport;
import sample.cafekiosk.spring.api.controller.order.request.OrderCreateServiceRequest;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class OrderControllerTest extends ControllerTestSupport {

  @DisplayName("신규 주문을 등록한다.")
  @Test
  void createOrder() throws Exception {

    // given
    OrderCreateServiceRequest request = OrderCreateServiceRequest.builder()
        .productNumbers(List.of("001"))
        .build();

    // when // then
    mockMvc.perform(
            post("/api/v1/orders/new")
                .content(objectMapper.writeValueAsString(request)) // POST HTTP Serialize -> Deserialize 하므로
                .contentType(MediaType.APPLICATION_JSON)
        )
        .andDo(print())
        .andExpect(status().isOk())
//        .andExpect(jsonPath("$.code").value("200"))
//        .andExpect(jsonPath("$.status").value("OK"))
//        .andExpect(jsonPath("$.message").value("OK"))
        ;

  }

}