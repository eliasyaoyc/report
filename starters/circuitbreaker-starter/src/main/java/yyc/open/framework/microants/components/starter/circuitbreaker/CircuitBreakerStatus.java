package yyc.open.framework.microants.components.starter.circuitbreaker;

/**
 * {@link CircuitBreakerStatus}
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/7/10
 */
public enum CircuitBreakerStatus {
    /**
     * A DISABLED breaker is not operating (no state transition, no events) and allowing all
     * requests through.
     */
    DISABLED(3, false),
    /**
     * A METRICS_ONLY breaker is collecting metrics, publishing events and allowing all requests
     * through but is not transitioning to other states.
     */
    METRICS_ONLY(5, true),
    /**
     * A CLOSED breaker is operating normally and allowing requests through.
     */
    CLOSED(0, true),
    /**
     * An OPEN breaker has tripped and will not allow requests through.
     */
    OPEN(1, true),
    /**
     * A FORCED_OPEN breaker is not operating (no state transition, no events) and not allowing
     * any requests through.
     */
    FORCED_OPEN(4, false),
    /**
     * A HALF_OPEN breaker has completed its wait interval and will allow requests
     */
    HALF_OPEN(2, true);

    public final boolean allowPublish;
    private final int order;

    CircuitBreakerStatus(int order, boolean allowPublish) {
        this.order = order;
        this.allowPublish = allowPublish;
    }

    public int getOrder() {
        return order;
    }
}
