package yyc.open.framework.microants.components.starter.circuitbreaker.autoconfigure;

import com.google.common.collect.Maps;
import org.springframework.boot.context.properties.ConfigurationProperties;
import yyc.open.framework.microants.components.starter.circuitbreaker.CircuitBreakerType;
import yyc.open.framework.microants.components.starter.circuitbreaker.MicroantsCircuitBreakerConfig;

import java.util.Map;

import static yyc.open.framework.microants.components.starter.circuitbreaker.autoconfigure.MicroantsCircuitBreakerProperties.MIROANTS_CIRCUIT_BREAKER;

/**
 * {@link MicroantsCircuitBreakerProperties}
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/7/10
 */
@ConfigurationProperties(prefix = MIROANTS_CIRCUIT_BREAKER)
public class MicroantsCircuitBreakerProperties {
    public static final String MIROANTS_CIRCUIT_BREAKER = "microants.circuit-breaker";

    private CircuitBreakerType type = CircuitBreakerType.R4J;

    private Map<String, MicroantsCircuitBreakerConfig> instances = Maps.newHashMap();

    private MicroantsCircuitBreakerConfig microantsCircuitBreakerConfig;

    public CircuitBreakerType getType() {
        return type;
    }

    public void setType(CircuitBreakerType type) {
        this.type = type;
    }

    public Map<String, MicroantsCircuitBreakerConfig> getInstances() {
        return instances;
    }

    public void setInstances(Map<String, MicroantsCircuitBreakerConfig> instances) {
        this.instances = instances;
    }

    public MicroantsCircuitBreakerConfig getMicroantsCircuitBreakerConfig() {
        return microantsCircuitBreakerConfig;
    }

    public void setMicroantsCircuitBreakerConfig(MicroantsCircuitBreakerConfig microantsCircuitBreakerConfig) {
        this.microantsCircuitBreakerConfig = microantsCircuitBreakerConfig;
    }
}
