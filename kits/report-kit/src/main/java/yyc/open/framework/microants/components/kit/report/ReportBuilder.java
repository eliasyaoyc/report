package yyc.open.framework.microants.components.kit.report;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * {@link ReportBuilder} main class that used to create customized file generator.
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/7/28
 */
public class ReportBuilder<T> {
    private List<ReportConfig> configs;
    private ReportConfig reportConfig;

    private ReportBuilder() {
        reportConfig = new ReportConfig();
    }

    public static ReportBuilder builder() {
        return new ReportBuilder();
    }

    public ReportBuilder colors(String... colors) {
        this.reportConfig.setColors(Arrays.stream(colors).collect(Collectors.toList()));
        return this;
    }

    public ReportBuilder colors(List<String> colors) {
        this.reportConfig.setColors(colors);
        return this;
    }

    public ReportBuilder type(String type) {
        this.reportConfig.setType(type);
        return this;
    }

    public ReportBuilder watermark(boolean watermark) {
        this.reportConfig.setWatermark(watermark);
        return this;
    }

    public ReportBuilder horizontal(boolean horizontal) {
        this.reportConfig.setHorizontal(horizontal);
        return this;
    }

    public ReportBuilder data(T data) {
        this.reportConfig.setData(data);
        return this;
    }

    public ReportBuilder and() {
        this.configs.add(reportConfig);
        reportConfig = new ReportConfig();
        return this;
    }

    public Report build() {
        this.configs.add(reportConfig);
        return new Report(this.configs);
    }
}
