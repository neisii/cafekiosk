package sample.cafekiosk.spring;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

/**
 * 테스트 환경 통합
 */
@ActiveProfiles("test")
@DataJpaTest
public abstract class JpaTestSupport {
}
