package yyc.open.framework.microants.components.starter.circuitbreaker;

import yyc.open.framework.microants.components.starter.circuitbreaker.exception.MicroantsCircuitBreakerException;

/**
 * {@link AbstractMicroantsCircuitBreaker}
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/7/10
 */
public class AbstractMicroantsCircuitBreaker implements MicroantsCircuitBreaker{
    private MicroantsCircuitBreaker microantsCircuitBreaker;

    @Override
    public void setSelf(Object proxyBean) {
        microantsCircuitBreaker = (MicroantsCircuitBreaker) proxyBean;
    }

    @Override
    public Object executeWrapper(Object... args) throws MicroantsCircuitBreakerException {
        return null;
    }

    @Override
    public Object fallback() {
        return null;
    }

    @Override
    public void publisherFailEvent() {
        microantsCircuitBreaker.publisherFailEvent();
    }

    @Override
    public void publisherFailEventDirectFallBack() {
        microantsCircuitBreaker.publisherFailEventDirectFallBack();
    }
}
