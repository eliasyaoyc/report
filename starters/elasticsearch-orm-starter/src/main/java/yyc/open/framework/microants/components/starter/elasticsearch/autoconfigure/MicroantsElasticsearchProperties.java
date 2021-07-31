package yyc.open.framework.microants.components.starter.elasticsearch.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * {@link MicroantsElasticsearchProperties}
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/7/10
 */
@ConfigurationProperties(prefix = MicroantsElasticsearchProperties.MIROANTS_ELASTICSEARCH)
public class MicroantsElasticsearchProperties {
    public static final String MIROANTS_ELASTICSEARCH= "microants.elasticsearch";
}
