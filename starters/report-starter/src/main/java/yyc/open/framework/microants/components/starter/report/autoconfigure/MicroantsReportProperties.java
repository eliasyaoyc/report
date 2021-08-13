package yyc.open.framework.microants.components.starter.report.autoconfigure;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

import static yyc.open.framework.microants.components.starter.report.autoconfigure.MicroantsReportProperties.MICROANTS_FILE;

/**
 * {@link MicroantsReportProperties}
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/7/28
 */
@Getter
@Setter
@ConfigurationProperties(prefix = MICROANTS_FILE)
public class MicroantsReportProperties {
    protected static final String MICROANTS_FILE = "microants.report";

    private Boolean echart = false;
    private List<String> colors;
    private String watermark;
    private Boolean horizontal;
    private Integer port;
    private Boolean parallel;
    private Integer maxTaskNum;
    private String outputPath;
    private String jsPath;
    private String execPath;
    private String templatesPath;
    private String jsPdfPath;
}
