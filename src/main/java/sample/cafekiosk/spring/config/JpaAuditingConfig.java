package sample.cafekiosk.spring.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing // BaseEntity Tracking : Config로 분리하면 Mock 테스트 시 Auditing 문제 방지
@Configuration
public class JpaAuditingConfig {

}
