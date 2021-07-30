package yyc.open.framework.microants.components.kit.report;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import yyc.open.framework.microants.components.kit.report.ReportContextFactory.ReportContextFactoryEnum;
import yyc.open.framework.microants.components.kit.report.exceptions.ReportException;

import java.util.List;

import static yyc.open.framework.microants.components.kit.report.ReportTask.createTask;

/**
 * {@link Report}
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/7/28
 */
public class Report {
    private static final Logger logger = LoggerFactory.getLogger(Report.class);

    private final List<ReportConfig> configs;

    public Report(List<ReportConfig> configs) {
        this.configs = configs;
    }

    /**
     * Generate the report that supports batch generation.
     *
     * @param configs
     */
    public void generateReport(List<ReportConfig> configs) {
        generate(configs, false);
    }

    /**
     * TODO next version.
     * <p>
     * Generate the report, in additional supports parallel generation.
     *
     * @param configs
     */
    private void generateReportParallel(List<ReportConfig> configs) {
        generate(configs, true);
    }


    private void generate(List<ReportConfig> configs, boolean parallel) {
        // 1. Check report status whether running
        if (!checkReportState()) {
            throw new ReportException("[Report Runtime] has not started, please start first.");
        }

        try {
            // 2. Create the task collections.
            List<ReportTask> tasks = createTask(configs);

            // 3. Handle task.
            ReportInstanceProvider.handlerFactory().handle(tasks);
        } catch (ReportException re) {
            logger.error("[Report] create task encounter error : {}", re);
            // Determine if an alarm is needed.
            if (needToAlarm()) {
                // send alarm.
                ReportEvent event = ReportEvent.builder()
                        .type(ReportEvent.EventType.FAIL)
                        .message(re.getMessage()) // including report id and name.
                        .build();
                ReportInstanceProvider.listenerFactory().onEvent(event);
            }
        }
    }

    /**
     * @return
     */
    boolean checkReportState() {
        ReportContext context = ReportContextFactoryEnum.INSTANCE.getReportContextFactory().getContext();
        ReportContext.ReportStatus reportStatus = context.getReportStatus();
        return true;
    }

    /**
     * Determine the alarm whether is enabled.
     *
     * @return true if alarm is enabled, otherwise false.
     */
    boolean needToAlarm() {
        ReportContext context = ReportContextFactoryEnum.INSTANCE.getReportContextFactory().getContext();
        ReportConfig.AlarmConfig alarm = context.getGlobalConfig().getAlarm();
        return alarm.isEnabled();
    }
}
