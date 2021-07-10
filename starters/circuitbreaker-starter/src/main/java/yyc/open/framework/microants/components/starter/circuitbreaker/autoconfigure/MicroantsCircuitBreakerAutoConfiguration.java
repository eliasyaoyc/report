package yyc.open.framework.microants.components.starter.circuitbreaker.autoconfigure;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.common.CompositeCustomizer;
import io.github.resilience4j.common.circuitbreaker.configuration.CircuitBreakerConfigurationProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import yyc.open.framework.microants.components.starter.circuitbreaker.MicroantsCircuitBreakerConfig;
import yyc.open.framework.microants.components.starter.circuitbreaker.spring.MicroantsCircuitBreakerBeanPostProcessor;

import java.util.Collections;
import java.util.Map;

import static yyc.open.framework.microants.components.starter.circuitbreaker.autoconfigure.MicroantsCircuitBreakerProperties.MIROANTS_CIRCUIT_BREAKER;

/**
 * {@link MicroantsCircuitBreakerAutoConfiguration}
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/7/10
 */
@Configuration
@EnableConfigurationProperties(MicroantsCircuitBreakerProperties.class)
public class MicroantsCircuitBreakerAutoConfiguration {

    private static final Logger log = LoggerFactory.getLogger(MicroantsCircuitBreakerAutoConfiguration.class);

    @Bean
    @Primary
    @ConditionalOnClass(CircuitBreakerRegistry.class)
    @ConditionalOnProperty(prefix = MIROANTS_CIRCUIT_BREAKER, value = "type", havingValue = "r4j")
    public CircuitBreakerRegistry circuitBreakerRegistry(MicroantsCircuitBreakerProperties properties) {

        log.info("[==MCB==] starting create r4j CircuitBreakerRegistry instance .");

        Map<String, MicroantsCircuitBreakerConfig> instances = properties.getInstances();
        CircuitBreakerRegistry registry = CircuitBreakerRegistry.ofDefaults();

        for (Map.Entry<String, MicroantsCircuitBreakerConfig> entry : instances.entrySet()) {
            CircuitBreakerConfigurationProperties temp = new CircuitBreakerConfigurationProperties();
            CircuitBreakerConfigurationProperties.InstanceProperties instanceProperties = new CircuitBreakerConfigurationProperties.InstanceProperties();
            BeanUtils.copyProperties(entry.getValue(), instanceProperties);

            CircuitBreakerConfig circuitBreakerConfig = temp.createCircuitBreakerConfig(entry.getKey(), instanceProperties, new CompositeCustomizer<>(Collections.emptyList()));
            log.info("[==MCB==] registry [{}] info r4j registry .", entry.getKey());
            registry.circuitBreaker(entry.getKey(), circuitBreakerConfig);
        }

        return registry;
    }

    @Bean
    public MicroantsCircuitBreakerBeanPostProcessor MicroantsCircuitBreakerBeanPostProcessor(MicroantsCircuitBreakerProperties properties) {
        return new MicroantsCircuitBreakerBeanPostProcessor(properties);
    }
}
