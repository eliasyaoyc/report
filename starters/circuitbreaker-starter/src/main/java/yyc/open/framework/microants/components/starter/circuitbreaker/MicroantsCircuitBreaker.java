package yyc.open.framework.microants.components.starter.circuitbreaker;

import yyc.open.framework.microants.components.starter.circuitbreaker.exception.MicroantsCircuitBreakerException;

/**
 * {@link MicroantsCircuitBreaker}
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/7/10
 */
public interface MicroantsCircuitBreaker<T> {
    void setSelf(Object proxyBean);

    /**
     * wrapper method support fallback
     *
     * @param args
     * @return
     * @throws MicroantsCircuitBreakerException
     * @see MicroantsCircuitBreakerException
     */
    T executeWrapper(Object... args) throws MicroantsCircuitBreakerException;

    /**
     * encounter exception will invoke this method
     *
     * @return
     */
    T fallback();

    /**
     * default method. when yourself method encounter unknown exception,
     * you can invoke this. will add circuitBreaker fail count. when reach the fail ratio you set
     * will invoke fallback
     */
    void publisherFailEvent();


    /**
     * compare method.publisherFailEvent , forced perform fallback.
     */
    void publisherFailEventDirectFallBack();
}
