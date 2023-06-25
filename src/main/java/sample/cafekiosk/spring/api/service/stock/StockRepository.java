package sample.cafekiosk.spring.api.service.stock;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sample.cafekiosk.spring.domain.stock.Stock;

import java.util.List;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {
  List<Stock> findAllByProductNumberIn(List<String> strings);
}
