package sample.cafekiosk.spring.api.service.mail;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import sample.cafekiosk.spring.client.mail.MailSendClient;
import sample.cafekiosk.spring.domain.history.mail.MailSendHistory;
import sample.cafekiosk.spring.domain.history.mail.MailSendHistoryRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
// 단위 테스트 실습
class MailServiceTest {

  /* Mock 객체 선언 2 : @ExtendWith(MockitoExtension.class) 와 같이 써야 함 */
//  @Mock
  @Mock
  private MailSendClient mailSendClient;

  @Mock
  private MailSendHistoryRepository mailSendHistoryRepository;

  @InjectMocks // DI 와 동일한 역할을 한다.
  private MailService mailService;

  @DisplayName("메일 전송 테스트")
  @Test
  void sendMail() {

    // given
    // Mock 객체 선언 1
//    MailSendClient mailSendClient = mock(MailSendClient.class);
//    MailSendHistoryRepository mailSendHistoryRepository = mock(MailSendHistoryRepository.class);
//    MailService mailService = new MailService(mailSendClient, mailSendHistoryRepository);


    // Mock: 실 객체를 Mocking함. stubbing
    // 결과: a(), b(), c() 의 로그가 찍히지 않는다.
//    Mockito.when(mailSendClient.sendMail(anyString(), anyString(), any(String.class), any(String.class)))
//        .thenReturn(true);
    BDDMockito.given(mailSendClient.sendMail(anyString(), anyString(), any(String.class), any(String.class)))
        .willReturn(true); // BDDMockito.given 절이 자연스럽기 때문에 Mockito 보다 권장한다.

    // Spy: 객체의 일부분만 stubbing
    // 예시는 sendMail 메서드만 stubbing 한다.
    // 결과: a(), b(), c() 의 로그가 찍힌다. === sendMail 외에는 실 객체로 수행한다.
//    doReturn(true)
//        .when(mailSendClient)
//        .sendMail(anyString(), anyString(), any(String.class), any(String.class));

    // when
    boolean result = mailService.sendMail("", "", "", "");

    // then
    assertThat(result).isTrue();


    verify(mailSendHistoryRepository, times(1)).save(any(MailSendHistory.class));
  }
}