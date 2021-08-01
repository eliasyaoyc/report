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
    private String watermark;
    private boolean horizontal;
    private T data;

    protected void setType(String type) {
        this.type = ReportEnums.getType(type);
    }

    public static ReportConfigBuilder builder() {
        return new ReportConfigBuilder();
    }

    static class ReportConfigBuilder<T> {
        private List<String> colors;
        private ReportEnums type;
        private String watermark;
        private boolean horizontal;
        private T data;

        public ReportConfig build() {
            return new ReportConfig();
        }
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
