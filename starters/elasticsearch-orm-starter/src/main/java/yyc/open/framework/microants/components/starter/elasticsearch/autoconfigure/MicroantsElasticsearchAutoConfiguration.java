package yyc.open.framework.microants.components.starter.elasticsearch.autoconfigure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * {@link MicroantsElasticsearchAutoConfiguration}
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/7/10
 */
@Configuration
@EnableConfigurationProperties(MicroantsElasticsearchProperties.class)
public class MicroantsElasticsearchAutoConfiguration {

    private static final Logger log = LoggerFactory.getLogger(MicroantsElasticsearchAutoConfiguration.class);
}
