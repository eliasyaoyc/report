package yyc.open.framework.microants.components.kit.report;

import org.apache.http.util.Asserts;
import yyc.open.framework.microants.components.kit.common.BeansKit;
import yyc.open.framework.microants.components.kit.common.validate.NonNull;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * {@link ReportBuilder} main class that used to create customized file generator.
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/7/28
 */
public class ReportBuilder {
    private final ReportConfig.GlobalConfig globalConfig;
    private ReportConfig reportConfig;

    private ReportBuilder() {
        reportConfig = new ReportConfig();
        globalConfig = ReportContextFactory.ReportContextFactoryEnum.INSTANCE
                .getReportContextFactory()
                .getContext()
                .getGlobalConfig();
    }

    public static ReportBuilder builder() {
        return new ReportBuilder();
    }

    /**
     * Setup the report config, if you do not set some parameters, `globalConfig`
     * will be used instead.
     *
     * @param reportConfig
     * @return
     */
    public ReportBuilder config(ReportConfig reportConfig) {
        this.reportConfig = reportConfig;
        return this;
    }

    /**
     * Setup charts colors, this priority is highest so use colors in global config
     * if you not set. Even worse, use the default if you haven't set any of them.
     *
     * @param colors
     * @return
     */
    public ReportBuilder colors(String... colors) {
        this.reportConfig.setColors(Arrays.stream(colors).collect(Collectors.toList()));
        return this;
    }

    public ReportBuilder colors(List<String> colors) {
        this.reportConfig.setColors(colors);
        return this;
    }

    /**
     * Setup the report type.
     *
     * @param type
     * @return
     */
    @NonNull
    public ReportBuilder type(String type) {
        this.reportConfig.setType(type);
        return this;
    }

    /**
     * Setup the watermark text, default opened if you input watermark text.
     *
     * @param watermark
     * @return
     */
    public ReportBuilder watermark(String watermark) {
        this.reportConfig.setWatermark(watermark);
        return this;
    }

    /**
     * Setup the chart whether horizontal.
     *
     * @param horizontal
     * @return
     */
    public ReportBuilder horizontal(boolean horizontal) {
        this.reportConfig.setHorizontal(horizontal);
        return this;
    }

    /**
     * Setup the report runtime port.
     *
     * @param port
     * @return
     */
    public ReportBuilder port(int port) {
        this.reportConfig.setPort(port);
        return this;
    }

    /**
     * Sets up the report whether parallel execution.
     *
     * @param parallel
     * @return
     */
    public ReportBuilder parallel(boolean parallel) {
        this.reportConfig.setParallel(parallel);
        return this;
    }

    /**
     * Sets up the phantom js path.
     *
     * @param execPath
     * @return
     */
    public ReportBuilder execPath(String execPath) {
        this.reportConfig.setExecPath(execPath);
        return this;
    }

    /**
     * Sets up the echarts template path.
     *
     * @param eChartJsPath
     * @return
     */
    public ReportBuilder eChartJsPath(String eChartJsPath) {
        this.reportConfig.setEChartJsPath(eChartJsPath);
        return this;
    }

    /**
     * Sets up the max task number can be received in once time.
     *
     * @param maxTaskNum
     * @return
     */
    public ReportBuilder maxTaskNum(int maxTaskNum) {
        this.reportConfig.setMaxTaskNum(maxTaskNum);
        return this;
    }

    /**
     * Enabled the alarm module, when encounter exception where to sent.
     *
     * @return
     */
    public ReportBuilder alarm() {
        ReportConfig.AlarmConfig alarmConfig = new ReportConfig.AlarmConfig();
        alarmConfig.setEnabled(true);
        this.reportConfig.alarm = alarmConfig;
        return this;
    }

    /**
     * Sets up the alarm type.
     *
     * @param type
     * @return
     */
    public ReportBuilder alarmType(String type) {
        ReportConfig.AlarmConfig alarm = this.reportConfig.getAlarm();
        if (alarm == null) {
            alarm = new ReportConfig.AlarmConfig();
            alarm.setEnabled(true);
        }
        this.reportConfig.alarm.setType(type);
        this.reportConfig.alarm = alarm;
        return this;
    }

    /**
     * Sets up the alarm address.
     *
     * @param address
     * @return
     */
    public ReportBuilder alarmAddress(String address) {
        ReportConfig.AlarmConfig alarm = this.reportConfig.getAlarm();
        if (alarm == null) {
            alarm = new ReportConfig.AlarmConfig();
            alarm.setEnabled(true);
        }
        this.reportConfig.alarm.setAddress(address);
        this.reportConfig.alarm = alarm;
        return this;
    }

    /**
     * Construct a default report.
     *
     * @return Report.
     */
    public static Report ofDefault() {
        return new ReportBuilder().build();
    }

    /**
     * Build the reportConfig, if you do not setup some parameters, `globalConfig` will
     * be used instead.
     *
     * @return Report.
     */
    public Report build() {
        // Check global config.
        Asserts.check(this.globalConfig != null, "Report Runtime initialize fail.");
        // Check report type whether is null (type is required parameter).
        Asserts.check(this.reportConfig.getType() == null, "[Constructor Report] the report type is must, please setup before build.");

        BeansKit.copyPropertiesIsNull(globalConfig, reportConfig);
        return new Report(reportConfig);
    }
}
