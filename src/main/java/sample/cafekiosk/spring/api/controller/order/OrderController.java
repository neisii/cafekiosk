package sample.cafekiosk.spring.api.controller.order;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import sample.cafekiosk.spring.api.controller.order.request.OrderCreateRequest;
import sample.cafekiosk.spring.api.service.order.OrderService;
import sample.cafekiosk.spring.api.service.order.response.OrderResponse;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@RestController
public class OrderController {

  private final OrderService orderService; // final이 있어야 생성자 주입 가능하다.

  @PostMapping("/api/v1/orders/new")
  public OrderResponse createOrder(@RequestBody OrderCreateRequest request) { // Request
    LocalDateTime registeredDateTime = LocalDateTime.now();
    return orderService.createOrder(request, registeredDateTime);
  }

}
