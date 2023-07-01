package sample.cafekiosk.unit;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.temporal.ChronoUnit;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ArgumentsSourceTest {

  /**
   * Note that an implementation of ArgumentsProvider must be declared as either a top-level class or as a static nested class.
   * @param argument
   */
  @ParameterizedTest
  @ArgumentsSource(MyArgumentsProvider.class)
  void testWithArgumentsSource(String argument) {
    assertNotNull(argument);
  }

  @ParameterizedTest
  @ValueSource(strings = "SECONDS")
  void testWithImplicitArgumentConversion(ChronoUnit argument) {
    assertNotNull(argument.name());
  }

}

class MyArgumentsProvider implements ArgumentsProvider {

  @Override
  public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
    return Stream.of("apple", "banana").map(Arguments::of);
  }
}