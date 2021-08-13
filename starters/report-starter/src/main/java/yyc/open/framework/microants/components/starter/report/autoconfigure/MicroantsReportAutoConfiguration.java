package yyc.open.framework.microants.components.starter.report.autoconfigure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import yyc.open.framework.microants.components.kit.common.BeansKit;
import yyc.open.framework.microants.components.kit.common.file.FileKit;
import yyc.open.framework.microants.components.kit.report.*;

/**
 * {@link MicroantsReportAutoConfiguration}
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/7/28
 */
@Configuration
@EnableConfigurationProperties(MicroantsReportProperties.class)
public class MicroantsReportAutoConfiguration {
    private static final Logger LOGGER = LoggerFactory.getLogger(MicroantsReportAutoConfiguration.class);

    @Bean
    @ConditionalOnClass(MicroantsReportProperties.class)
    public Report report(MicroantsReportProperties properties) {
        String property = System.getProperty("os.name");
        ReportPlatforms platforms = ReportPlatforms.getPlatforms(property);

        ReportConfig reportConfig = new ReportConfig();
        BeansKit.copyProperties(properties, reportConfig);

        // Generate absolute path.
        reportConfig.setExecPath(properties.getExecPath() + platforms.getPath());
        reportConfig.setJsPath(properties.getJsPath());
        reportConfig.setJsPdfPath(properties.getJsPdfPath());
        reportConfig.setTemplatesPath(properties.getTemplatesPath());

        ReportRuntime reportRuntime = new ReportRuntime();
        reportRuntime.start(properties.getEchart(), FileKit.getResourcePath(reportConfig.getExecPath()), FileKit.getResourcePath(reportConfig.getJsPath()));
        LOGGER.info("[ReportRuntime] start success.");

        return ReportBuilder.builder().config(reportConfig).build();
    }

    public static void main(String[] args) {
        String resourcePath = FileKit.getResourcePath("report/exec/phantomjs-macosx");
        System.out.println(resourcePath);
    }
}
