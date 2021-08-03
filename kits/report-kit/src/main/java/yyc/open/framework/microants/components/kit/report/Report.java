package yyc.open.framework.microants.components.kit.report;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import yyc.open.framework.microants.components.kit.common.validate.NonNull;
import yyc.open.framework.microants.components.kit.report.entity.ReportEntity;
import yyc.open.framework.microants.components.kit.report.exceptions.ReportException;
import yyc.open.framework.microants.components.kit.report.handler.ReportHandlerFactory;
import yyc.open.framework.microants.components.kit.report.threadpool.ReportThreadPool;

import java.util.List;
import java.util.concurrent.CountDownLatch;

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
    private ReportThreadPool threadPool;
    private ReportStatus reportStatus;

    public Report(@NonNull ReportConfig config, @NonNull ReportStatus status) {
        this.reportStatus = status;
        this.config = config;
        this.handlerFactory = ReportHandlerFactory
                .HandlerFactoryEnum
                .INSTANCE
                .getReportHandlerFactory(status);
        this.taskRegistry = ReportTaskRegistry
                .ReportRegistryEnum
                .INSTANCE
                .getReportRegistry(status);
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

        if (CollectionUtils.isEmpty(reportEntities)) {
            return;
        }

        // 2. Create the task collections.
        List<ReportTask> tasks = taskRegistry.createTask(config, reportEntities);
        if (CollectionUtils.isEmpty(tasks)) {
            return;
        }

        CountDownLatch latch = new CountDownLatch(tasks.size());
        // 3. Handle task.
        this.handlerFactory.handle(tasks, parallel, new ReportCallback() {
            @Override
            public void onReceived(String taskId, ReportEvent.EventType type) {
                latch.countDown();
                reportStatus.publishEvent(taskId, "", ReportEvent.EventType.PARTIALLY_COMPLETED);
            }

            @Override
            public void onException(String taskId, String msg) {
                reportStatus.publishEvent(taskId, msg, ReportEvent.EventType.FAIL);
            }
        });

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
