package yyc.open.framework.microants.components.kit.audit;

import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Elias (siran0611@gmail.com)
 */
public class SpanCollector {

	private static final Logger LOG = LoggerFactory.getLogger(SpanCollector.class);

	private Map<String, Set<Span>> spans = Maps.newConcurrentMap();
}
