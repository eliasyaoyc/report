package yyc.open.framework.microants.components.kit.audit;

import com.google.common.collect.Lists;
import java.util.List;

/**
 * @author Elias (siran0611@gmail.com)
 */
public class TracingFactory {

	protected static final TracingFactory INSTANCE = new TracingFactory();

	private List<TracingInterceptor> interceptors = Lists.newArrayList();

	private TracingFactory() {
	}

}
