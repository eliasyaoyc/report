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
public class ReportConfig<T> {
    private List<String> colors;
    private ReportEnums type;
    private boolean watermark;
    private boolean horizontal;
    private T data;

    public void setType(String type) {
        this.type = ReportEnums.getType(type);
    }

    @Getter
    @Setter
    static class GlobalConfig {
        int port;
        boolean watermark;
        boolean horizontal;
        boolean parallel;
        boolean chart;
        String execPath;
        String eChartJsPath;
        AlarmConfig alarm;

    }

    @Getter
    @Setter
    static class AlarmConfig {
        boolean enabled;
        String type;
        String address;
    }
}
