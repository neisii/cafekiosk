
# cafekiosk
cafekiosk practical testing

[Practical Testing: 실용적인 테스트 가이드 by 박우빈](https://www.inflearn.com/course/practical-testing-%EC%8B%A4%EC%9A%A9%EC%A0%81%EC%9D%B8-%ED%85%8C%EC%8A%A4%ED%8A%B8-%EA%B0%80%EC%9D%B4%EB%93%9C/dashboard)

## Classicist vs Mockist
* 우리 시스템 - 외부 시스템
* 외부 시스템은 항상 정상 작동한다고 가정한다.
* 외부 시스템에 대한 Mocking 처리하는 게 좋다. (by Classicist)
*
## 더 나은 테스트를 작성하기 위한 구체적 조언
* 한 문단에 한 주제
  * 한 테스트에서는 한가지 검증만 수행하도록 한다.
  * 반복 분기는 지양한다.
* 완벽하게 제어하기
  * 제어할 수 없는 코드(예: 현재 시간, 랜덤 값)
    * 완벽한 테스트를 구성할 수 있는가?
      * 현재 시간과 같은 제어할 수 없는 값은 지양하라.
      * 현재 시간 쓰지 말자!!
* 테스트 환경의 독립성을 보장하자
  * 결합성에 대해 독립성을 보장하라
  * 가능한 생성자를 활용하라
  * Factory 메서드는 지양하고 Builder 나 순수한 생성자를 활용하는 것이 독립성을 보장하기 쉽다.
* 테스트 간 독립성을 보장하자
  * 두 개 이상의 테스트의 독립성을 보장하자
  * 공유 자원을 사용하지 마라.
    * 테스트 간 순서에 따라 테스트가 실패할 수 있다.
* 한 눈에 들어오는 Test Fixture 구성하기
  * Test Fixture: given 절에서 생성한 모든 객체들을 지칭한다.
  * @BeforeAll: 테스트 전 수행, @BeforeEach: 테스트 메서드 전에 수행
  * @AfterEach: 테스트 후 @AfterAll : 테스트 클래스 전체가 끝난 후 수행