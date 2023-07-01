package sample.cafekiosk.unit;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class MethodSourceTest {

  /**
   * User defined factory method
   * @return Stream
   */
  static Stream<String> stringProvider() {
    return Stream.of("apple", "banana");
  }

  @DisplayName("stringProvider라는 이름의 팩토리 메서드를 매개변수로써 사용한다.")
  @ParameterizedTest
  @MethodSource("stringProvider")
  void testWithExplicitLocalMethodSource(String argument) {
    assertNotNull(argument);
  }

  /**
   * Default name of factory method
   * @return Stream
   */
  static Stream<String> testWithDefaultLocalMethodSource() {
    return Stream.of("apple", "banana");
  }

  @DisplayName("팩토리 메서드명이 제공되지 않는 경우 JUnit5 의 기본 규칙에 따라 testWithDefaultLocalMethodSource()를 찾아서 사용한다.")
  @ParameterizedTest
  @MethodSource
  void testWithDefaultLocalMethodSource(String argument) {
    assertNotNull(argument);
  }

  @DisplayName("특정 범위를 매개변수로 사용한다.")
  @ParameterizedTest
  @MethodSource("range")
  void testWithRangeMethodSource(int argument) {
    assertNotEquals(9, argument);
  }

  static IntStream range() {
    return IntStream.range(0, 20).skip(10);
  }

  @DisplayName("여러 매개변수를 사용하는 경우 Arguments.arguments로 매개변수를 사용한다.")
  @ParameterizedTest
  @MethodSource("stringIntAndListProvider")
  void testWithMultiArgMethodSource(String str, int num, List<String> list) {
    assertEquals(5, str.length());
    assertTrue(num >=1 && num <=2);
    assertEquals(2, list.size());
  }

  static Stream<Arguments> stringIntAndListProvider() {
    return Stream.of(
        arguments("apple", 1, Arrays.asList("a", "b")),
        arguments("lemon", 2, Arrays.asList("x", "y"))
    );
  }


}

class ExternalMethodSourceDemoTest {

  @DisplayName("외부 static 팩토리 메서드는 정규화된 메서드 이름을 제공하여 참조할 수 있다.")
  @ParameterizedTest
  @MethodSource("sample.cafekiosk.unit.StringsProviders#tinyStrings")
  void testWithExternalMethodSource(String tinyString) {
    // test with tiny string
  }
}

class StringsProviders {

  static Stream<String> tinyStrings() {
    return Stream.of(".", "oo", "OOO");
  }
}

