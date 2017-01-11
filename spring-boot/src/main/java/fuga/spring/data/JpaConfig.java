package fuga.spring.data;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Spring Data JPAのための設定。
 * AuditingEntityListenerを動かすのにEnableJpaAuditingが必要。
 *
 * @author irof
 */
@Configuration
@EnableJpaAuditing
public class JpaConfig {
}
