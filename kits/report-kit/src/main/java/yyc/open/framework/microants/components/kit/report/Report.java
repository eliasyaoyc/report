package yyc.open.framework.microants.components.kit.report;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import yyc.open.framework.microants.components.kit.report.ReportContextFactory.ReportContextFactoryEnum;
import yyc.open.framework.microants.components.kit.report.entity.ReportEntity;
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

    private final ReportConfig config;

    public Report(ReportConfig config) {
        this.config = config;
    }

    /**
     * Generate the report that supports batch generation.
     */
    public void generateReport(List<ReportEntity> reportEntities) {
        generate(reportEntities, false);
    }

    /**
     * TODO next version.
     * <p>
     * Generate the report, in additional supports parallel generation.
     */
    private void generateReportParallel(List<ReportEntity> reportEntities) {
        generate(reportEntities, true);
    }


    private void generate(List<ReportEntity> reportEntities, boolean parallel) {
        // 1. Check report status whether running
        if (!checkReportState()) {
            throw new ReportException("[Report Runtime] has not started, please start first.");
        }

        try {
            // 2. Create the task collections.
            List<ReportTask> tasks = createTask(config, reportEntities);

            // 3. Handle task.
            ReportContext.handlerFactory().handle(tasks, parallel);
        } catch (ReportException re) {
            String msg = String.format("[Report] create task encounter error : {}", re);
            logger.error(msg);
            // send alarm.
            ReportEvent event = ReportEvent.builder()
                    .type(ReportEvent.EventType.FAIL)
                    .message(re.getMessage()) // including report id and name.
                    .message(msg)
                    .build();
            ReportContext.listenerFactory().onEvent(event);
        }
    }

    /**
     * Check the report runtime whether running.
     *
     * @return true if report is running, otherwise return false.
     */
    boolean checkReportState() {
        ReportContext context = ReportContextFactoryEnum.INSTANCE.getReportContextFactory().getContext();
        ReportContext.ReportStatus reportStatus = context.getReportStatus();
        return true;
    }
}
