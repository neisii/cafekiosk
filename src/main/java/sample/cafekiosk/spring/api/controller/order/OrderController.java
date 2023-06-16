package sample.cafekiosk.spring.api.controller.order;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import sample.cafekiosk.spring.api.controller.order.request.OrderCreateRequest;
import sample.cafekiosk.spring.api.service.order.OrderService;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@RestController
public class OrderController {

  private final OrderService orderService; // final은 생성자 주입을 위함.

  @PostMapping("/api/v1/orders/new")
  public void createOrder(OrderCreateRequest request) {

    LocalDateTime registeredDateTime = LocalDateTime.now();
    orderService.createOrder(request, registeredDateTime);
  }

}
