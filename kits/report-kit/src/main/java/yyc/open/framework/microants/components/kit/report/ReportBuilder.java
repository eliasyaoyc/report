package yyc.open.framework.microants.components.kit.report;

import org.apache.http.util.Asserts;
import yyc.open.framework.microants.components.kit.common.BeansKit;

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
    private ReportConfig reportConfig;
    private ReportStatus status;

    private ReportBuilder() {
        this.reportConfig = new ReportConfig();
        this.status = ReportStatus.ReportStatusEnum.INSTANCE.getReportStatus();
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
        ReportConfig.GlobalConfig globalConfig = ReportContextProvider.INSTANCE.getContext().getGlobalConfig();

        // Check global config.
        Asserts.check(globalConfig != null, "Report Runtime initialize fail.");

        BeansKit.copyPropertiesIsNull(globalConfig, reportConfig);

        // Create report status.
        return new Report(reportConfig, status);
    }
}
