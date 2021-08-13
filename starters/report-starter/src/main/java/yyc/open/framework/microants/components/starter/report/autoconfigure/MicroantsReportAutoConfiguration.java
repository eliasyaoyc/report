package yyc.open.framework.microants.components.starter.report.autoconfigure;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import yyc.open.framework.microants.components.kit.report.Report;
import yyc.open.framework.microants.components.kit.report.ReportBuilder;
import yyc.open.framework.microants.components.kit.report.ReportConfig;
import yyc.open.framework.microants.components.kit.report.ReportRuntime;

import static yyc.open.framework.microants.components.starter.report.autoconfigure.MicroantsReportProperties.MICROANTS_FILE;

/**
 * {@link MicroantsReportAutoConfiguration}
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/7/28
 */
@Configuration
@EnableConfigurationProperties(MicroantsReportProperties.class)
public class MicroantsReportAutoConfiguration {

    @Bean
    @Primary
    @ConditionalOnClass(MicroantsReportProperties.class)
    @ConditionalOnProperty(prefix = MICROANTS_FILE, value = "echart", havingValue = "enabled")
    public ReportRuntime reportEchartsRuntime() {
        ReportRuntime runtime = new ReportRuntime();
        runtime.start(true);
        return runtime;
    }

    @Bean
    @ConditionalOnMissingBean(value = ReportRuntime.class)
    public ReportRuntime reportRuntime() {
        ReportRuntime runtime = new ReportRuntime();
        runtime.start(false);
        return runtime;
    }

    @Bean
    @ConditionalOnClass(MicroantsReportProperties.class)
    public Report report(MicroantsReportProperties properties) {
        ReportConfig reportConfig = properties.getReportConfig();
        return ReportBuilder.builder().config(reportConfig).build();
    }
}
