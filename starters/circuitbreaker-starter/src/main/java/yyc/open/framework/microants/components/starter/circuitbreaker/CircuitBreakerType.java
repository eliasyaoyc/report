package yyc.open.framework.microants.components.starter.circuitbreaker;

/**
 * {@link CircuitBreakerType}
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/7/10
 */
public enum CircuitBreakerType {
    /**
     * Resilience4j Implements
     *
     * <p>
     */
    R4J,

    /**
     * Alibaba Sentinel Implements
     *
     * <p>
     */
    STL
}
