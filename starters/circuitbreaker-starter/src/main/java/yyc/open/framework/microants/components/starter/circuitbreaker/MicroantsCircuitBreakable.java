package yyc.open.framework.microants.components.starter.circuitbreaker;

import yyc.open.framework.microants.components.starter.circuitbreaker.exception.MicroantsCircuitBreakerDirectThrowException;
import yyc.open.framework.microants.components.starter.circuitbreaker.exception.MicroantsCircuitBreakerException;

import java.util.concurrent.TimeUnit;

/**
 * {@link MicroantsCircuitBreakable}
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/7/10
 */
public interface MicroantsCircuitBreakable {

    String DEFAULT_FALLBACK_METHOD_NAME = "$fallback0";

    /**
     * Records a failed call. This method must be invoked when a call failed.
     *
     * @param duration The elapsed time duration of the call
     * @param durationUnit The duration unit
     * @param throwable The throwable which must be recorded
     * @throws MicroantsCircuitBreakerException maybe thrown {@link MicroantsCircuitBreakerException}
     */
    void firing(long duration, TimeUnit durationUnit, Throwable throwable) throws MicroantsCircuitBreakerException;

    /**
     * Biz Service Throw Custom Biz Exception .
     *
     * @param exception custom biz exception , WARN: sub-exception must extends {@link RuntimeException}
     * @throws MicroantsCircuitBreakerException maybe thrown {@link MicroantsCircuitBreakerException}
     */
    void firing(RuntimeException exception) throws MicroantsCircuitBreakerDirectThrowException;

    /**
     * Records a successful call. This method must be invoked when a call was successful.
     *
     * @param duration The elapsed time duration of the call
     * @param durationUnit The duration unit
     * @throws MicroantsCircuitBreakerException maybe thrown {@link MicroantsCircuitBreakerException}
     */
    void ack(long duration, TimeUnit durationUnit) throws MicroantsCircuitBreakerException;

    /**
     * Returns the state of this CircuitBreaker
     *
     * @return the state of this CircuitBreaker
     */
    CircuitBreakerStatus getStatus();

    /**
     * Service Method Execute Failed, Framework will call {@link MicroantsCircuitBreakable#$fallback0(Throwable)} finally .
     *
     * @param throwable fallback with throwable .
     * @return fallback return result object.
     * @throws MicroantsCircuitBreakerException maybe thrown {@link MicroantsCircuitBreakerException}
     */
    Object $fallback0(Throwable throwable) throws MicroantsCircuitBreakerException;
}
