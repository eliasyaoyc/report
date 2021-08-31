package yyc.open.framework.microants.components.starter.audit.autoconfigure;

import static yyc.open.framework.microants.components.starter.audit.autoconfigure.MicroantsAuditProperties.MICROANTS_AUDIT;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Elias (siran0611@gmail.com)
 */
@Getter
@Setter
@ConfigurationProperties(prefix = MICROANTS_AUDIT)
public class MicroantsAuditProperties {

	protected static final String MICROANTS_AUDIT = "microants.audit";

	private int disruptorBufferSize;
}
