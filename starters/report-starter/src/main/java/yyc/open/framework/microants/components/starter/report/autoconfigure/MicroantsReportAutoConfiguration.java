package yyc.open.framework.microants.components.starter.report.autoconfigure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import yyc.open.framework.microants.components.kit.common.BeansKit;
import yyc.open.framework.microants.components.kit.report.Report;
import yyc.open.framework.microants.components.kit.report.ReportBuilder;
import yyc.open.framework.microants.components.kit.report.ReportConfig;
import yyc.open.framework.microants.components.kit.report.ReportRuntime;

import java.io.File;

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
        ReportConfig reportConfig = new ReportConfig();
        BeansKit.copyProperties(properties, reportConfig);

        if (!new File(reportConfig.getOutputPath()).isAbsolute()) {
            String parentPath = System.getProperty("user.dir");
            File file;
            if (parentPath.endsWith(File.separator) || reportConfig.getOutputPath().startsWith(File.separator)) {
                file = new File(parentPath + reportConfig.getOutputPath());
            } else {
                file = new File(parentPath + File.separator + reportConfig.getOutputPath());
            }
            if (!file.exists()) {
                file.mkdirs();
            }
            reportConfig.setOutputPath(file.getAbsolutePath());
        }

        ReportRuntime reportRuntime = new ReportRuntime();
        reportRuntime.start(properties.getEchart());
        LOGGER.info("[ReportRuntime] start success.");

        return ReportBuilder.builder().config(reportConfig).build();
    }
}
