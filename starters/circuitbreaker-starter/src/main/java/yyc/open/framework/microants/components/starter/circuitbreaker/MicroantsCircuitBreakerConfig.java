package yyc.open.framework.microants.components.starter.circuitbreaker;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;

import java.io.Serializable;
import java.time.Duration;

/**
 * {@link MicroantsCircuitBreakerConfig}
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/7/10
 */
public class MicroantsCircuitBreakerConfig implements Serializable {
    private static final long serialVersionUID = 7162508292174433502L;


    private Float slowCallRateThreshold = 100.0f;

    private Duration slowCallDurationThreshold = Duration.ofMillis(60000);

    private Boolean automaticTransitionFromOpenToHalfOpenEnabled = true;

    private Duration waitDurationInOpenState = Duration.ofSeconds(50);

    private Float failureRateThreshold = 50.0f;

    private Integer slidingWindowSize = 3;

    private Integer minimumNumberOfCalls = 3;

    private Integer permittedNumberOfCallsInHalfOpenState = 3;

    private Integer eventConsumerBufferSize = 5;

    private Class<? extends Throwable>[] recordExceptions;

    private Class<? extends Throwable>[] ignoreExceptions;


    private CircuitBreakerConfig.SlidingWindowType slidingWindowType = CircuitBreakerConfig.SlidingWindowType.COUNT_BASED;

    public Float getSlowCallRateThreshold() {
        return slowCallRateThreshold;
    }

    public void setSlowCallRateThreshold(Float slowCallRateThreshold) {
        this.slowCallRateThreshold = slowCallRateThreshold;
    }

    public Duration getSlowCallDurationThreshold() {
        return slowCallDurationThreshold;
    }

    public void setSlowCallDurationThreshold(Duration slowCallDurationThreshold) {
        this.slowCallDurationThreshold = slowCallDurationThreshold;
    }

    public Boolean getAutomaticTransitionFromOpenToHalfOpenEnabled() {
        return automaticTransitionFromOpenToHalfOpenEnabled;
    }

    public void setAutomaticTransitionFromOpenToHalfOpenEnabled(Boolean automaticTransitionFromOpenToHalfOpenEnabled) {
        this.automaticTransitionFromOpenToHalfOpenEnabled = automaticTransitionFromOpenToHalfOpenEnabled;
    }

    public Duration getWaitDurationInOpenState() {
        return waitDurationInOpenState;
    }

    public void setWaitDurationInOpenState(Duration waitDurationInOpenState) {
        this.waitDurationInOpenState = waitDurationInOpenState;
    }

    public Float getFailureRateThreshold() {
        return failureRateThreshold;
    }

    public void setFailureRateThreshold(Float failureRateThreshold) {
        this.failureRateThreshold = failureRateThreshold;
    }

    public Integer getSlidingWindowSize() {
        return slidingWindowSize;
    }

    public void setSlidingWindowSize(Integer slidingWindowSize) {
        this.slidingWindowSize = slidingWindowSize;
    }

    public Integer getMinimumNumberOfCalls() {
        return minimumNumberOfCalls;
    }

    public void setMinimumNumberOfCalls(Integer minimumNumberOfCalls) {
        this.minimumNumberOfCalls = minimumNumberOfCalls;
    }

    public Integer getPermittedNumberOfCallsInHalfOpenState() {
        return permittedNumberOfCallsInHalfOpenState;
    }

    public void setPermittedNumberOfCallsInHalfOpenState(Integer permittedNumberOfCallsInHalfOpenState) {
        this.permittedNumberOfCallsInHalfOpenState = permittedNumberOfCallsInHalfOpenState;
    }

    public Integer getEventConsumerBufferSize() {
        return eventConsumerBufferSize;
    }

    public void setEventConsumerBufferSize(Integer eventConsumerBufferSize) {
        this.eventConsumerBufferSize = eventConsumerBufferSize;
    }

    public Class<? extends Throwable>[] getRecordExceptions() {
        return recordExceptions;
    }

    public void setRecordExceptions(Class<? extends Throwable>[] recordExceptions) {
        this.recordExceptions = recordExceptions;
    }

    public Class<? extends Throwable>[] getIgnoreExceptions() {
        return ignoreExceptions;
    }

    public void setIgnoreExceptions(Class<? extends Throwable>[] ignoreExceptions) {
        this.ignoreExceptions = ignoreExceptions;
    }

    public CircuitBreakerConfig.SlidingWindowType getSlidingWindowType() {
        return slidingWindowType;
    }

    public void setSlidingWindowType(CircuitBreakerConfig.SlidingWindowType slidingWindowType) {
        this.slidingWindowType = slidingWindowType;
    }
}
