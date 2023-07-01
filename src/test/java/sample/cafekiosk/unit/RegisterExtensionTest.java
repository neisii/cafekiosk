package sample.cafekiosk.unit;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolver;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@Disabled
public class RegisterExtensionTest {

  @RegisterExtension
  static final IntegerResolver integerResolver = new IntegerResolver();

  @DisplayName("TODO: 설명 추가하기")
  @ParameterizedTest
  @MethodSource("factoryMethodWithArguments")
  void testWithFactoryMethodWithArguments(String argument) {
    assertTrue(argument.startsWith("2"));
  }

  static Stream<Arguments> factoryMethodWithArguments(int quantity) {
    return Stream.of(
        arguments(quantity + " apples"),
        arguments(quantity + " lemons")
    );
  }

  static class IntegerResolver implements ParameterResolver {

    @Override
    public boolean supportsParameter(ParameterContext parameterContext,
                                     ExtensionContext extensionContext) {

      return parameterContext.getParameter().getType() == int.class;
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext,
                                   ExtensionContext extensionContext) {

      return 2;
    }


  }
}