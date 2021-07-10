package yyc.open.framework.microants.components.starter.circuitbreaker.proxy;

import org.aopalliance.intercept.MethodInvocation;
import org.aopalliance.intercept.MethodInterceptor;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import yyc.open.framework.microants.components.kit.reflect.ReflectKit;
import yyc.open.framework.microants.components.starter.circuitbreaker.MicroantsCircuitBreakable;
import yyc.open.framework.microants.components.starter.circuitbreaker.MicroantsCircuitBreakerAction;
import yyc.open.framework.microants.components.starter.circuitbreaker.event.DefaultEventConsumer;
import yyc.open.framework.microants.components.starter.circuitbreaker.exception.MicroantsCircuitBreakerException;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import static java.util.concurrent.TimeUnit.NANOSECONDS;
import static yyc.open.framework.microants.components.starter.circuitbreaker.MicroantsCircuitBreakable.DEFAULT_FALLBACK_METHOD_NAME;

/**
 * {@link AbstractMicroantsCircuitBreakerActionMethodInterceptor}
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/7/10
 */
public abstract class AbstractMicroantsCircuitBreakerActionMethodInterceptor implements MethodInterceptor {
    private static final Logger log = LoggerFactory.getLogger(AbstractMicroantsCircuitBreakerActionMethodInterceptor.class);

    protected final MicroantsCircuitBreakable breakable;

    private Method fallbackMethod;

    protected AbstractMicroantsCircuitBreakerActionMethodInterceptor(MicroantsCircuitBreakable breakable) {
        this.breakable = breakable;
        Assert.notNull(this.breakable, "Microants Circuit Breakable Instance Object Must not be null .");
        fallbackMethod = ReflectKit.getAccessibleMethod(this.breakable, DEFAULT_FALLBACK_METHOD_NAME, Throwable.class);
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {

        Object[] args = invocation.getArguments();

        boolean isFallbackMethodOverride = false;
        boolean assignableFrom = false;
        boolean isAnnotationMethod = false;
        try {
            Method method = invocation.getMethod();

            if (method.isAnnotationPresent(MicroantsCircuitBreakerAction.class)) {
                isAnnotationMethod = true;
                MicroantsCircuitBreakerAction action = method.getAnnotation(MicroantsCircuitBreakerAction.class);

                String resourceName = action.name();

                Class customEventConsumer = action.customEventConsumer();
                // register first
                if (!customEventConsumer.isAssignableFrom(DefaultEventConsumer.class)) {
                    this.registry(resourceName, customEventConsumer);
                } else {
                    this.registry(resourceName);
                }


                String methodName = action.fallbackMethod();

                Class<? extends Throwable>[] classes = action.customExceptions();


                if (DEFAULT_FALLBACK_METHOD_NAME.equalsIgnoreCase(methodName)) {
                    throw new MicroantsCircuitBreakerException(String.format("[==MCB==] custom fallback method name must not named : '%s'", DEFAULT_FALLBACK_METHOD_NAME));
                } else {
                    try {
                        Class<?>[] parameterTypes = method.getParameterTypes();
                        Method customFallbackMethod = ReflectKit.getAccessibleMethod(breakable, methodName, parameterTypes);
                        isFallbackMethodOverride = customFallbackMethod != null;

                        if (isFallbackMethodOverride) {
                            fallbackMethod = customFallbackMethod;
                        }
                    } catch (Exception e) {
                        log.warn("[==MCB==] found fallback method with name : [" + methodName + "] failed , fallback method must has the same signature with origin service method ", e);
                    }
                }

                Assert.notNull(fallbackMethod, "CircuitBreaker s fallback method must not be null .");

                long start = System.nanoTime();

                boolean acquired = this.tryAcquire(resourceName);

                if (acquired) {
                    try {
                        Object result = invocation.proceed();
                        this.breakable.ack((System.nanoTime() - start), NANOSECONDS);
                        return result;

                    } catch (Exception e) {

                        this.breakable.firing((System.nanoTime() - start), NANOSECONDS, e);

                        // if invoked happened custom exception , just throw exception
                        for (Class<? extends Throwable> aClass : classes) {
                            assignableFrom = e.getClass().isAssignableFrom(aClass);
                            if (assignableFrom) {
                                log.error("encounter custom exception : {}", e.getMessage());
                                throw e;
                            }
                        }

                        return this.invokedFallbackMethod(breakable, fallbackMethod, isFallbackMethodOverride ? args : e);
                    }
                }

                // DEFAULT RETURN NULL.
                // ONLY WHEN INVOKED HAPPENED EXCEPTION & FAILED ATTEMPTED MAX COUNTS .
                return this.invokedFallbackMethod(breakable, fallbackMethod, isFallbackMethodOverride ? args : null);

            } else {
                // execute real method directly
                return invocation.proceed();
            }

        } catch (Throwable e) {

            if (assignableFrom || !isAnnotationMethod) {
                throw e;
            }

            // exception.
            if (e instanceof MicroantsCircuitBreakerException) {

            }

            //
            return this.invokedFallbackMethod(
                    breakable,
                    fallbackMethod,
                    isFallbackMethodOverride ? args : new MicroantsCircuitBreakerException("CircuitBreaker s proxy method execute failed ", e));
        }
    }

    private Object invokedFallbackMethod(Object instance, Method method, Object... args) {
        try {
            method.setAccessible(true);
            return method.invoke(instance, args);
        } catch (Exception e) {
            e.printStackTrace();
            log.warn("[==MCB==] fallback method execute failed, just return null.");
            return null;
        }
    }

    /**
     * Try to Register Context Instance
     *
     * @param resourceName action resource name
     * @throws MicroantsCircuitBreakerException maybe thrown {@link MicroantsCircuitBreakerException}
     */
    protected abstract void registry(String resourceName) throws MicroantsCircuitBreakerException;


    protected abstract void registry(String resourceName, Class eventConsumer) throws MicroantsCircuitBreakerException;


    /**
     * Try to required access permission
     *
     * @param resourceName action resource name
     * @return true / false , if return true , thread will execute real target service method .
     * @throws MicroantsCircuitBreakerException maybe thrown {@link MicroantsCircuitBreakerException}
     */
    protected abstract boolean tryAcquire(String resourceName) throws MicroantsCircuitBreakerException;
}
