package sample.cafekiosk.spring;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import sample.cafekiosk.spring.client.mail.MailSendClient;

/**
 * 테스트 환경 통합
 */
@ActiveProfiles("test")
@SpringBootTest
public abstract class IntergrationTestSupport {


  @MockBean // 사실 서버를 새로 띄워야 하는 케이스
  // 1. 상위 클래스에 올려서 protected 처리하여 하위에서 사용한다.
  // 2. 테스트 환경을 두 개로 나눈다. Mocking, 상위 클래스를 상속하도록
  protected MailSendClient mailSendClient;
}
