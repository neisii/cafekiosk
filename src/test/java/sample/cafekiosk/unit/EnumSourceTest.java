package sample.cafekiosk.unit;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.EnumSet;

import static org.junit.jupiter.api.Assertions.*;

public class EnumSourceTest {

  @DisplayName("유닛에 포함된")
  @ParameterizedTest
  @EnumSource(ChronoUnit.class)
  void testWithEnumSource(TemporalUnit unit) {
    assertNotNull(unit);
  }

  @DisplayName("Enum에 포함된")
  @ParameterizedTest
  @EnumSource(names = { "DAYS", "HOURS" })
  void testWithEnumSourceInclude(ChronoUnit unit) {
    assertTrue(EnumSet.of(ChronoUnit.DAYS, ChronoUnit.HOURS).contains(unit));
  }

  @DisplayName("EnumSource 정규식")
  @ParameterizedTest
  @EnumSource(mode = EnumSource.Mode.EXCLUDE, names = { "ERAS", "FOREVER" })
  void testWithEnumSourceExclude(ChronoUnit unit) {
    assertFalse(EnumSet.of(ChronoUnit.ERAS, ChronoUnit.FOREVER).contains(unit));
  }

  @DisplayName("EnumSource 정규식")
  @ParameterizedTest
  @EnumSource(mode = EnumSource.Mode.MATCH_ALL, names = "^.*DAYS$")
  void testWithEnumSourceRegex(ChronoUnit unit) {
    assertTrue(unit.name().endsWith("DAYS"));
  }

}

