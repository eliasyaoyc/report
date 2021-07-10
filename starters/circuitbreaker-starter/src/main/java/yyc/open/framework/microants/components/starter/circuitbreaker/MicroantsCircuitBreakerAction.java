package yyc.open.framework.microants.components.starter.circuitbreaker;

import yyc.open.framework.microants.components.starter.circuitbreaker.event.DefaultEventConsumer;

import java.lang.annotation.*;

import static yyc.open.framework.microants.components.starter.circuitbreaker.MicroantsCircuitBreakable.DEFAULT_FALLBACK_METHOD_NAME;

/**
 * {@link MicroantsCircuitBreakerAction}
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/7/10
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MicroantsCircuitBreakerAction {

    /**
     * Action Name Defined
     *
     * <p>default: class-full-name#mehod-name
     *
     * @return name of action for circuit breaker
     */
    String name() default "default";

    /**
     * Target Fallback Method Name
     * @return method name
     */
    String fallbackMethod() default DEFAULT_FALLBACK_METHOD_NAME;

    /**
     * Action Custom Exceptions Defined.
     *
     * If nothing is set, it is not turned on by default.
     * Conversely, an exception is thrown when an exception is encountered in the Settings
     *
     * @return
     */
    Class<? extends Throwable>[] customExceptions() default {};

    /**
     * Action Custom Event Consumer.
     *
     * If nothing is set, it is not turned on by {@link DefaultEventConsumer}
     * Conversely, an Event publish by CircuitBreaker published will be caught {@link io.github.resilience4j.core.EventConsumer}
     *
     * @return
     */
    Class customEventConsumer() default DefaultEventConsumer.class;
}
