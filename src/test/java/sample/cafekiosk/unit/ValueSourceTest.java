package sample.cafekiosk.unit;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ValueSourceTest {

  @DisplayName("1개 인자 부여")
  @ParameterizedTest
  @ValueSource(ints = { 1, 2, 3 })
  void testWithValueSource(int argument) {
    assertTrue(argument > 0 && argument < 4);
  }

  @DisplayName("Null 과 빈값 이스케이프 문자")
  @ParameterizedTest
//  @NullAndEmptySource // Null, EmptySource를 결합한 것과 동일한 역할이다.
  @NullSource
  @EmptySource
  @ValueSource(strings = { " ", "   ", "\t", "\n" })
  void nullEmptyAndBlankStrings(String text) {
    assertTrue(text == null || text.trim().isEmpty());
  }

}