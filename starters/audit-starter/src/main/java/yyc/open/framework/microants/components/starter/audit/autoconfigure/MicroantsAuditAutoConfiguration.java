package yyc.open.framework.microants.components.starter.audit.autoconfigure;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import yyc.open.framework.microants.components.kit.audit.Audit;
import yyc.open.framework.microants.components.kit.audit.AuditBuilder;

/**
 * @author Elias (siran0611@gmail.com)
 */
@Configuration
@EnableConfigurationProperties(MicroantsAuditProperties.class)
public class MicroantsAuditAutoConfiguration {

	@Bean
	@ConditionalOnClass(MicroantsAuditProperties.class)
	public Audit report(MicroantsAuditProperties properties) {
		return AuditBuilder.newInstance()
				.disruptorBufferSize(properties.getDisruptorBufferSize())
				.build();
	}
}
