package yyc.open.framework.microants.components.starter.circuitbreaker.spring;

import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import yyc.open.framework.microants.components.starter.circuitbreaker.MicroantsCircuitBreakable;
import yyc.open.framework.microants.components.starter.circuitbreaker.autoconfigure.MicroantsCircuitBreakerProperties;
import yyc.open.framework.microants.components.starter.circuitbreaker.proxy.MicroantsResilience4jCircuitBreakerActionMethodInterceptor;

/**
 * {@link MicroantsCircuitBreakerBeanPostProcessor}
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/7/10
 */
public class MicroantsCircuitBreakerBeanPostProcessor implements BeanPostProcessor, ApplicationContextAware {
    private final MicroantsCircuitBreakerProperties properties;

    private ApplicationContext applicationContext;

    /**
     * Default Constructor For Bean Post Processor
     *
     * @param properties instance of {@link MicroantsCircuitBreakerProperties}
     */
    public MicroantsCircuitBreakerBeanPostProcessor(
             MicroantsCircuitBreakerProperties properties) {
        this.properties = properties;
    }

    @Override
    public void setApplicationContext( ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public Object postProcessAfterInitialization( Object bean,  String beanName)
            throws BeansException {

        if (bean instanceof MicroantsCircuitBreakable) {
            ProxyFactory factory = new ProxyFactory(bean);
            factory.setProxyTargetClass(true);
            factory.addInterface(MicroantsCircuitBreakable.class);
            factory.setExposeProxy(true);

            switch (properties.getType()) {
                case STL:
                    throw new RuntimeException("Un-supported alibaba sentinel plugin.");

                case R4J:
                default:
                    ObjectProvider<CircuitBreakerRegistry> provider = applicationContext.getBeanProvider(CircuitBreakerRegistry.class);
                    factory.addAdvice(new MicroantsResilience4jCircuitBreakerActionMethodInterceptor((MicroantsCircuitBreakable) bean, provider));
                    break;
            }

            return factory.getProxy();
        }

        return bean;
    }
}
