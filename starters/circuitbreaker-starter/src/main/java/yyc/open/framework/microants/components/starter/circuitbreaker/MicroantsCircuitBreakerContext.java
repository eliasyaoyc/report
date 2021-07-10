package yyc.open.framework.microants.components.starter.circuitbreaker;

import yyc.open.framework.microants.components.starter.circuitbreaker.exception.MicroantsCircuitBreakerException;

/**
 * {@link MicroantsCircuitBreakerContext}
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/7/10
 */
public interface MicroantsCircuitBreakerContext<Handler> {
    /**
     * Register Circuit Breaker Context Handler
     *
     * @param handler handler instance
     * @throws MicroantsCircuitBreakerException maybe thrown {@link MicroantsCircuitBreakerException}
     */
    void register(Handler handler) throws MicroantsCircuitBreakerException;
}
