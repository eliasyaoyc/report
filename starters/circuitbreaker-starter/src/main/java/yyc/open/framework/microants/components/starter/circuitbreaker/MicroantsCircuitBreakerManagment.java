package yyc.open.framework.microants.components.starter.circuitbreaker;

import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import java.util.Map;

/**
 * {@link MicroantsCircuitBreakerManagment}
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/7/10
 */
public class MicroantsCircuitBreakerManagment {
    private static final Logger log = LoggerFactory.getLogger(MicroantsCircuitBreakerManagment.class);

    /**
     * Cached Circuit Breaker Instance(s)
     *
     * <p>
     */
    private static final Map<CircuitBreakerType, MicroantsCircuitBreakable> CONTEXTS = Maps.newConcurrentMap();

    /**
     * Register Circuit Breaker Instance
     *
     * @param type      type of {@link CircuitBreakerType}
     * @param breakable instance of {@link MicroantsCircuitBreakable}
     */
    public static @Nullable
    MicroantsCircuitBreakable register(CircuitBreakerType type, MicroantsCircuitBreakable breakable) {
        return CONTEXTS.put(type, breakable);
    }

    /**
     * Get Circuit Breaker Instance By {@link CircuitBreakerType}
     *
     * @param type type of {@link CircuitBreakerType}
     * @return instance of {@link MicroantsCircuitBreakable}
     */
    public static MicroantsCircuitBreakable get(CircuitBreakerType type) {
        return CONTEXTS.get(type);
    }

}
