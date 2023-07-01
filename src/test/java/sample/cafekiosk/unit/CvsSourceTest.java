package sample.cafekiosk.unit;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CvsSourceTest {

  /*
   * Example Input | Resulting Argument List
                   // 따옴표로 묶인 문자열 내를 제외한 CSV 열의 선행 및 후행 공백은 기본적으로 잘린다.
                   @CsvSource({ "apple, banana" }) | "apple", "banana"
                   @CsvSource({ "apple, 'lemon, lime'" }) | "apple", "lemon, lime"
                   @CsvSource({ "apple, ''" }) | "apple", ""
                   @CsvSource({ "apple, " }) | "apple", null
                   @CsvSource(value = { "apple, banana, NIL" }, nullValues = "NIL") | "apple", "banana", null

                   // 공백 자르지 않기
                   @CsvSource(value = { " apple , banana" }, ignoreLeadingAndTrailingWhitespace = false) | " apple ", " banana"
   *
   */
  @DisplayName("콤마로 구분된 데이터")
  @ParameterizedTest
  @CsvSource({
      "apple,         1",
      "banana,        2",
      "'lemon, lime', 0xF1",
      "strawberry,    700_000"
  })
  void testWithCsvSource(String fruit, int rank) {
    assertNotNull(fruit);
    assertNotEquals(0, rank);
  }

  @ParameterizedTest(name = "[{index}] {arguments}") // text blocks are supported in -source 15
  @CsvSource(useHeadersInDisplayName = true, textBlock = """
    FRUIT,         RANK
    apple,         1
    banana,        2
    'lemon, lime', 0xF1
    strawberry,    700_000
    """)
  void testWithCsvSource2(String fruit, int rank) {
    assertNotNull(fruit);
    assertNotEquals(0, rank);
  }

  @ParameterizedTest
  @CsvSource(delimiter = '|', quoteCharacter = '"', textBlock = """
    #-----------------------------
    #    FRUIT     |     RANK
    #-----------------------------
         apple     |      1
    #-----------------------------
         banana    |      2
    #-----------------------------
      "lemon lime" |     0xF1
    #-----------------------------
       strawberry  |    700_000
    #-----------------------------
    """)
  void testWithCsvSource3(String fruit, int rank) {
    // ...
  }

  @ParameterizedTest
  @CsvFileSource(resources = "/two-column.csv", numLinesToSkip = 1)
  void testWithCsvFileSourceFromClasspath(String country, int reference) {
    assertNotNull(country);
    assertNotEquals(0, reference);
  }

  @ParameterizedTest
  @CsvFileSource(files = "src/test/resources/two-column.csv", numLinesToSkip = 1)
  void testWithCsvFileSourceFromFile(String country, int reference) {
    assertNotNull(country);
    assertNotEquals(0, reference);
  }

  @ParameterizedTest(name = "[{index}] {arguments}")
  @CsvFileSource(resources = "/two-column.csv", useHeadersInDisplayName = true)
  void testWithCsvFileSourceAndHeaders(String country, int reference) {
    assertNotNull(country);
    assertNotEquals(0, reference);
  }

}