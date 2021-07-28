package yyc.open.framework.microants.components.starter.report.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;
import yyc.open.framework.microants.components.kit.report.ReportConfig;

import static yyc.open.framework.microants.components.starter.report.autoconfigure.MicroantsFileProperties.MICROANTS_FILE;

/**
 * {@link MicroantsFileProperties}
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/7/28
 */
@ConfigurationProperties(prefix = MICROANTS_FILE)
public class MicroantsFileProperties {
    protected static final String MICROANTS_FILE = "microants.file";

    private ReportConfig reportConfig;

    public ReportConfig getReportConfig() {
        return reportConfig;
    }

    public void setReportConfig(ReportConfig reportConfig) {
        this.reportConfig = reportConfig;
    }
}
