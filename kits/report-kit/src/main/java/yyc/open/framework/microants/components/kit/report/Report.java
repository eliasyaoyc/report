package yyc.open.framework.microants.components.kit.report;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import yyc.open.framework.microants.components.kit.report.entity.ReportEntity;
import yyc.open.framework.microants.components.kit.report.exceptions.ReportException;
import yyc.open.framework.microants.components.kit.report.handler.ReportHandlerFactory;

import java.util.List;

/**
 * {@link Report}
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/7/28
 */
public class Report {
    private static final Logger logger = LoggerFactory.getLogger(Report.class);

    private final ReportConfig config;
    private final ReportHandlerFactory handlerFactory;
    private final ReportTaskRegistry taskRegistry;

    public Report(ReportConfig config) {
        this.config = config;
        this.handlerFactory = ReportHandlerFactory
                .HandlerFactoryEnum
                .INSTANCE
                .getReportHandlerFactory();
        this.taskRegistry = ReportTaskRegistry
                .ReportRegistryEnum
                .INSTANCE
                .getReportRegistry();
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

        // 2. Create the task collections.
        List<ReportTask> tasks = taskRegistry.createTask(config, reportEntities);

        // 3. Handle task.
        this.handlerFactory.handle(tasks, parallel);
    }

    /**
     * Check the report runtime whether running.
     *
     * @return true if report is running, otherwise return false.
     */
    boolean checkReportState() {
        return ReportContextProvider.INSTANCE.getContext().getReportStatus() == null;
    }
}
