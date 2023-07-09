
# cafekiosk
cafekiosk practical testing

[Practical Testing: 실용적인 테스트 가이드 by 박우빈](https://www.inflearn.com/course/practical-testing-%EC%8B%A4%EC%9A%A9%EC%A0%81%EC%9D%B8-%ED%85%8C%EC%8A%A4%ED%8A%B8-%EA%B0%80%EC%9D%B4%EB%93%9C/dashboard)

---
# Classicist vs Mockist
* 우리 시스템 - 외부 시스템
* 외부 시스템은 항상 정상 작동한다고 가정한다.
* 외부 시스템에 대한 Mocking 처리하는 게 좋다. (by Classicist)

# 더 나은 테스트를 작성하기 위한 구체적 조언
## 한 문단에 한 주제
* 한 테스트에서는 한가지 검증만 수행하도록 한다.
  * 반복 분기는 지양한다.
## 완벽하게 제어하기
* 제어할 수 없는 코드(예: 현재 시간, 랜덤 값)는 지양하라.
  * 완벽한 테스트를 구성할 수 있어야 한다.
  * 현재 시간 쓰지 말자!!
## 테스트 환경의 독립성을 보장하자
* 결합성에 대해 독립성을 보장하라
  * 가능한 생성자를 활용하라
    * 독립성 보장하기
      * Factory 메서드는 지양하고(예: Order.create(...))
      * Builder 사용
      * 또는 순수한 생성자를 활용하라.
## 테스트 간 독립성을 보장하자
* 두 개 이상의 테스트의 독립성을 보장하자
* 공유 자원을 사용 하지 마라.
  * 테스트 간 순서에 따라 테스트가 실패할 수 있다.
## 한 눈에 들어오는 Test Fixture 구성하기
* Test Fixture: given 절에서 생성한 모든 객체들을 지칭한다.
* @BeforeAll: 테스트 전 수행
* @BeforeEach: 테스트 메서드 전에 수행
  * 동일한 Fixture가 반복될 때 setUp에서 수행하도록 공유 자원을 지양하는 것과 동일한 역할을 한다.
    * 각 테스트 입장에서 봤을 때, 아예 몰라도 테스트 내용을 이해하는 데에 문제가 없는 객체만 선언한다.
    * 수정해도 모든 테스트에 영향을 주지 않는 객체만 선언한다.
* @AfterAll : 테스트 클래스 전체가 끝난 후 수행
* @AfterEach: 테스트 후 수행
* data.sql 은 지양하라.
  * given 데이터가 많은 경우 사용할 수도 있으나 코드가 파편화되어 유지보수가 어려워진다.
* 테스트 내 메서드는 꼭 필요한 필드만 명시하라.
* 테스트를 위한 빌더는 각 테스트 클래스 별로 정의하여 사용하라.
  * 필요하면 목적에 맞게 빌더를 생성하도록
  * 코틀린은 하나의 생성자만 사용하므로 편하다!


## Fixture cleansing
* deleteAll()과 deleteAllInBatch() 의 차이점
* `deleteAllInBatch()`
  * delete 순서가 중요하다.
  * 외래키 관계가 존재하는 경우 `한번에 삭제` 가능 
* deleteAll()
  * `개별 데이터 row를 삭제`한다.
  * select와 delete 순으로 하므로 연관 데이터를 함께 조회하여 삭제하므로 `상대적으로 테스트 비용이 많이 나간다.`
    * 실행되는 쿼리가 많기 때문에 시간이 많이 걸림
* @Transactional (Transaction rollback) 은 side-effect를 잘 고려하면 편리하다.


## @ParamterizedTest
* 테스트에 분기나 반복문이 들어가는 경우
* `테스트 환경이나 값을 변경해가며 테스트` 하는 경우 유용하다.
* https://junit.org/junit5/docs/current/user-guide/#writing-tests-parameterized-tests
  * @CsvSource 와 함께 사용
  * @MethodSource: 테스트할 파라미터를 메서드화하여 사용한다.
    * 사용하는 테스트 바로 위에 선언한다.
  * @ValueSource: 파라미터가 하나인 경우, 테스트 과정이 동일한 경우 사용한다.
    * ```@ValueSource(strings = { " ", "   ", "\t", "\n" })```
  * @EnumSource: Enum 객체 
  * @EmptySource: 원시 배열, 객체 배열
    * ```java.lang.String, java.util.List, java.util.Set, java.util.Map, 기본 배열(int[], char[][] 등), 객체 배열(String[], Integer[][] 등).```
  * @NullSoruce: 
  * @NullAndEmptySoruce: @NullSoruce 와 @EmptySoruce 
  * 참고: SpockFramework: Markdown 느낌의 테스트 케이스를 작성할 수 있다.
## @DynamicTest
  * 공유 자원 테스트
  * 일련의 시나리오 테스트 시 사용한다.
    * `@TestFactory`
## 테스트 수행도 비용이다. 환경 통합하기
* 통합 테스트 너무 오래 걸림.
* 어떻게 해야 할까
* `서버를 덜 띄우면 된다!`
  * 공통은 상위로 분리하여 상속하라.
## private 메서드의 테스트는 어떻게 하나요?
* 분리할 필요 없고 분리해서도 안된다.
* 생각해보기: 객체를 분리할 시점인가?
  * `책임이 분리되어야 하는가?`
## 테스트에서만 필요한 메서드가 생겼는데 프로덕션 코드에서는 필요 없다면?
* 파라미터로 받는 ProductCreateRequest.builder() 빌더 생성자는 테스트에서만 사용한다. 이런 경우??
  * 만들어도 된다. 하지만 `보수적`으로 접근하라.
    * `기능 명세에 맞는 테스트를 작성하라.`
    * `@builder, size() 정도는 OK`
## 키워드 정리

## Spring REST Docs
* Spring REST Docs
  * 테스트 코드를 통한 API 문서 자동화 도구
  * API 명세를 문서로 만들고 외부에 제공함으로써 협업을 원활하게 한다.
  * 기본적으로 AsciiDoc을 사용하여 문서를 작성한다.
* REST Docs vs. Swagger
  * REST Docs
    * 테스트를 통과해야 문서가 만들어진다.(신뢰도가 높다.)
    * 프로덕션 코드에 비침투적
    * but, 코드 양이 많다
    * 설정이 어렵다.
  * Swagger
    * 적용이 쉬움
    * 문서에서 바로 API 호출을 수행해볼 수 있다.
    * but, 프로덕션 코드에 비침투적 (코드가 드러워질 수 있다.)
    * 테스트와 무관하기 때문에 상대적으로 신뢰도가 떨어질 수 있음.
* 