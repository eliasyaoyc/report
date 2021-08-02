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
    private boolean horizontal;
    private int port;
    private boolean parallel;
    private String execPath;
    private String eChartJsPath;
    AlarmConfig alarm;
    private int maxTaskNum;

    protected void setType(String type) {
        this.type = ReportEnums.getType(type);
    }

    @Getter
    @Setter
    public static class GlobalConfig {
        int port;
        String watermark;
        boolean horizontal;
        boolean parallel;
        boolean chart;
        String execPath;
        String eChartJsPath;
        AlarmConfig alarm;
        List<String> colors;
        int maxTaskNum;
    }

    @Getter
    @Setter
    public static class AlarmConfig {
        boolean enabled;
        String type;
        String address;
    }
}
