package yyc.open.framework.microants.components.kit.report;

import lombok.Getter;
import lombok.Setter;
import yyc.open.framework.microants.components.kit.report.commons.ReportEnums;

import java.util.List;

/**
 * {@link ReportConfig}
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/7/28
 */
@Getter
@Setter
public class ReportConfig {
    private List<String> colors;
    private ReportEnums type;
    private String watermark;
    private Boolean horizontal;
    private Integer port;
    private Boolean parallel;
    AlarmConfig alarm;
    private Integer maxTaskNum;
    private String outputPath;

    protected void setType(String type) {
        this.type = ReportEnums.getType(type);
    }

    @Getter
    @Setter
    public static class GlobalConfig {
        Integer port;
        String watermark;
        Boolean horizontal;
        Boolean parallel;
        Boolean chart;
        AlarmConfig alarm;
        List<String> colors;
        Integer maxTaskNum;
        String outputPath;
    }

    @Getter
    @Setter
    public static class AlarmConfig {
        boolean enabled;
        String type;
        String address;
    }
}
