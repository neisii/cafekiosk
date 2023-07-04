package sample.cafekiosk.learning;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Guava 학습 테스트 
 */
class GuavaLearingTest {

  @DisplayName("주어진 개수만큼 List를 파티셔닝한다.")
  @Test
  void partitionLearningTest1() {

    // given
    List<Integer> integers = List.of(1, 2, 3, 4, 5, 6);

    // when
    List<List<Integer>> partition = Lists.partition(integers, 3);

    // then
    assertThat(partition).hasSize(2)
        .isEqualTo(List.of(
            List.of(1,2,3), List.of(4,5,6)
        ));
  }

  @DisplayName("주어진 개수만큼 List를 파티셔닝한다.")
  @Test
  void partitionLearningTest2() {

    // given
    List<Integer> integers = List.of(1, 2, 3, 4, 5, 6);

    // when
    List<List<Integer>> partition = Lists.partition(integers, 4); // size, 원소의 개수

    // then
    assertThat(partition).hasSize(2)
        .isEqualTo(List.of(
            List.of(1,2,3,4), List.of(5,6)
        ));
  }

  @DisplayName("멀티맵 기능 확인")
  @Test
  void multiMapLearningTest() {

    // given
    Multimap<String, String> multimap = ArrayListMultimap.create();
    // Map Key:Val 1:1
    // Multimap: Key:Val 1:n
    multimap.put("커피", "아메리카노");
    multimap.put("커피", "카페라떼");
    multimap.put("커피", "카푸치노");
    multimap.put("베이커리", "크루아상");
    multimap.put("베이커리", "식빵");

    // when
    Collection<String> strings = multimap.get("커피");

    // then
    assertThat(strings).hasSize(3)
        .isEqualTo(List.of("아메리카노", "카페라떼", "카푸치노"));
  }

  @DisplayName("멀티맵 기능 확인")
  @TestFactory
  Collection<DynamicTest> multiMapLearningTest2() {

    // given
    Multimap<String, String> multimap = ArrayListMultimap.create();
    // Map Key:Val 1:1
    // Multimap: Key:Val 1:n
    multimap.put("커피", "아메리카노");
    multimap.put("커피", "카페라떼");
    multimap.put("커피", "카푸치노");
    multimap.put("베이커리", "크루아상");
    multimap.put("베이커리", "식빵");

    return List.of(
        DynamicTest.dynamicTest("1개 value 삭제", () -> {
          // when
          multimap.remove("커피", "카푸치노");

          // then
          Collection<String> strings = multimap.get("커피");
          assertThat(strings).hasSize(2)
              .isEqualTo(List.of("아메리카노", "카페라떼"));
        }),
        DynamicTest.dynamicTest("전체 value 삭제", () -> {
          // when
          multimap.removeAll("커피");

          // then
          Collection<String> strings = multimap.get("커피");
          assertThat(strings).isEmpty();
        })
    );
  }

}
