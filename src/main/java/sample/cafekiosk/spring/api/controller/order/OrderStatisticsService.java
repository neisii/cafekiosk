package sample.cafekiosk.spring.api.controller.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sample.cafekiosk.spring.domain.order.Order;
import sample.cafekiosk.spring.domain.order.OrderRepository;
import sample.cafekiosk.spring.domain.order.OrderStatus;

import java.time.LocalDate;
import java.util.List;

import static sample.cafekiosk.spring.domain.order.OrderStatus.PAYMENT_COMPLETED;

@RequiredArgsConstructor
@Service
public class OrderStatisticsService {

  OrderRepository orderRepository;

  public void sendOrderStatisticsMail(LocalDate orderDate, String email) {
    // 해당 일자에 결제 완료된 주문들을 가져와서
    List<Order> list = orderRepository.findOrdersBy(
        orderDate.atStartOfDay(),
        orderDate.plusDays(1).atStartOfDay(),
        PAYMENT_COMPLETED
    );

    // 총 매출 합계룰 계산하고

    //  메일 전송



  }


}
