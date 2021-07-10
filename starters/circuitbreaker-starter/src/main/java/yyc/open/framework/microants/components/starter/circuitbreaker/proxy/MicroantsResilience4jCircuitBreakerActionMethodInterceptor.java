package yyc.open.framework.microants.components.starter.circuitbreaker.proxy;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.core.EventConsumer;
import io.github.resilience4j.core.EventProcessor;
import org.springframework.beans.factory.ObjectProvider;
import yyc.open.framework.microants.components.starter.circuitbreaker.AbstractMicroantsResilience4jCircuitBreaker;
import yyc.open.framework.microants.components.starter.circuitbreaker.MicroantsCircuitBreakable;
import yyc.open.framework.microants.components.starter.circuitbreaker.exception.MicroantsCircuitBreakerException;

/**
 * {@link MicroantsResilience4jCircuitBreakerActionMethodInterceptor}
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/7/10
 */
public class MicroantsResilience4jCircuitBreakerActionMethodInterceptor extends AbstractMicroantsCircuitBreakerActionMethodInterceptor{
    private final ObjectProvider<CircuitBreakerRegistry> circuitBreakerRegistryObjectProvider;

    public MicroantsResilience4jCircuitBreakerActionMethodInterceptor(
            MicroantsCircuitBreakable breakable,
            ObjectProvider<CircuitBreakerRegistry> circuitBreakerRegistryObjectProvider) {
        super(breakable);
        this.circuitBreakerRegistryObjectProvider = circuitBreakerRegistryObjectProvider;
    }

    @Override
    protected void registry(String resourceName) throws MicroantsCircuitBreakerException {

        CircuitBreakerRegistry registry = circuitBreakerRegistryObjectProvider.getIfAvailable();

        if (registry != null) {
            if (breakable instanceof AbstractMicroantsResilience4jCircuitBreaker) {
                AbstractMicroantsResilience4jCircuitBreaker breaker = (AbstractMicroantsResilience4jCircuitBreaker) breakable;
                CircuitBreaker circuitBreaker = registry.circuitBreaker(resourceName);
                breaker.register(circuitBreaker);
            }
        }

    }

    @Override
    protected void registry(String resourceName, Class eventConsumer) throws MicroantsCircuitBreakerException {
        CircuitBreakerRegistry registry = circuitBreakerRegistryObjectProvider.getIfAvailable();

        if (registry != null) {
            if (breakable instanceof AbstractMicroantsResilience4jCircuitBreaker) {
                AbstractMicroantsResilience4jCircuitBreaker breaker = (AbstractMicroantsResilience4jCircuitBreaker) breakable;
                CircuitBreaker circuitBreaker = registry.circuitBreaker(resourceName);
                try {
                    EventConsumer consumer = (EventConsumer) eventConsumer.newInstance();
                    EventProcessor processor = (EventProcessor) circuitBreaker.getEventPublisher();
                    if (!processor.hasConsumers()) {
                        processor.onEvent(consumer);
                    }
                    breaker.register(circuitBreaker);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Try to required access permission
     *
     * @param resourceName action resource name
     * @return true / false
     * @throws MicroantsCircuitBreakerException maybe thrown {@link MicroantsCircuitBreakerException}
     */
    @Override
    protected boolean tryAcquire(String resourceName) throws MicroantsCircuitBreakerException {

        CircuitBreakerRegistry registry = circuitBreakerRegistryObjectProvider.getIfAvailable();

        if (registry != null) {
            CircuitBreaker circuitBreaker = registry.circuitBreaker(resourceName);
            return circuitBreaker.tryAcquirePermission();
        }

        return false;
    }

}
