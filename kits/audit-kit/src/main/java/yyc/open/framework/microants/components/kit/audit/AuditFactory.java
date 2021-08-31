package yyc.open.framework.microants.components.kit.audit;

import com.google.common.collect.Lists;
import java.util.List;

/**
 * @author Elias (siran0611@gmail.com)
 */
public class AuditFactory {

	protected static final AuditFactory INSTANCE = new AuditFactory();

	private List<AuditInterceptor> interceptors = Lists.newArrayList();

	private AuditFactory() {
	}

}
