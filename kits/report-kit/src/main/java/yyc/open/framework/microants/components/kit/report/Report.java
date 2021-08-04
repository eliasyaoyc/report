package yyc.open.framework.microants.components.kit.report;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.http.util.Asserts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import yyc.open.framework.microants.components.kit.common.validate.NonNull;
import yyc.open.framework.microants.components.kit.report.exceptions.ReportException;
import yyc.open.framework.microants.components.kit.report.handler.ReportHandlerFactory;

import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * {@link Report} core class generates report methods external provision.
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/7/28
 */
public class Report {
    private static final Logger LOGGER = LoggerFactory.getLogger(Report.class);

    private final ReportConfig config;
    private final ReportHandlerFactory handlerFactory;
    private final ReportTaskRegistry taskRegistry;
    private ReportThreadPool threadPool;
    private ReportStatus reportStatus;

    public Report(@NonNull ReportConfig config, @NonNull ReportStatus status) {
        this.reportStatus = status;
        this.config = config;
        this.threadPool = new ReportThreadPool("report");
        this.handlerFactory = ReportHandlerFactory
                .HandlerFactoryEnum
                .INSTANCE
                .getReportHandlerFactory(config);
        this.taskRegistry = ReportTaskRegistry
                .ReportRegistryEnum
                .INSTANCE
                .getReportRegistry(status);
    }

    /**
     * Generate the report that supports batch generation.
     */
    public void generateReport(List<ReportMetadata> reportEntities) {
        generate(reportEntities, false);
    }

    /**
     * TODO next version.
     * <p>
     * Generate the report, in additional supports parallel generation.
     */
    private void generateReportParallel(List<ReportMetadata> reportEntities) {
        generate(reportEntities, true);
    }


    private void generate(List<ReportMetadata> reportEntities, boolean parallel) {
        // 1. Check report status whether running
        if (checkReportState()) {
            throw new ReportException("[Report Runtime] has not started, please start first.");
        }

        if (CollectionUtils.isEmpty(reportEntities)) {
            return;
        }

        // 2. Create the task collections.
        reportEntities.stream().forEach(entity -> {
            // Check report type whether is null (type is required parameter).
            Asserts.check(entity.getReportType() != null, "[Constructor Report] the report type is must, please setup before build.");

            List<ReportTask> tasks = taskRegistry.createTask(config, entity);
            if (CollectionUtils.isEmpty(tasks)) {
                return;
            }

            CountDownLatch latch = new CountDownLatch(tasks.size());

            tasks.stream().forEach(task -> {
                // 3. Handle task.
                threadPool.execute(() -> {
                    this.handlerFactory.handle(task, parallel, new ReportCallback() {
                        @Override
                        public void onReceived(String taskId, String result, ReportEvent.EventType type) {
                            switch (type) {
                                case PARTIALLY_COMPLETED:
                                    reportStatus.publishEvent(taskId, ReportEvent.EventType.PARTIALLY_COMPLETED);
                                    latch.countDown();
                                    // Add task result.
                                    entity.setSubTaskExecutionResult(taskId, result);
                                    break;
                                case COMPLETED:
                                    reportStatus.publishEvent(taskId, ReportEvent.EventType.COMPLETED);
                                    break;
                            }
                        }

                        @Override
                        public void onException(String taskId, String msg) {
                            LOGGER.error("[Report] subTask{}-{} execute failed.", entity.getReportId(), taskId);
                            reportStatus.publishEvent(taskId, msg, ReportEvent.EventType.FAIL);
                            // Add to fail queue that ready to re-execute.
                            taskRegistry.addToFailQueue(taskId);
                        }
                    });
                });
            });
            try {
                // The block waits for all subtasks to complete.
                latch.await();

                // Start execute root task(report).
//                reportStatus.publishEvent(entity.getReportId(), ReportEvent.EventType.REPORT);
//                this.handlerFactory.handle(entity, parallel, new ReportCallback() {
//                    @Override
//                    public void onReceived(String taskId, String result, ReportEvent.EventType type) {
//                        reportStatus.publishEvent(taskId, ReportEvent.EventType.COMPLETED);
//                    }
//
//                    @Override
//                    public void onException(String id, String msg) {
//                        LOGGER.error("[Report] {} execute failed.", id);
//                        reportStatus.publishEvent(id, msg, ReportEvent.EventType.FAIL);
//                        // Add to fail queue that ready to re-execute.
//                        taskRegistry.addToFailQueue(id);
//                    }
//                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Check the report runtime whether running.
     *
     * @return true if report is running, otherwise return false.
     */
    boolean checkReportState() {
        return ReportContextProvider.INSTANCE.getContext().getReportStatus() == null;
    }

    /**
     * Verity the last generation report whether valid.
     * First, we should check whether has values what content type is charts.
     * Next, checks report corresponding attributes is not empty.
     *
     * @param entity Report metadata.
     */
    boolean validReportEntity(ReportMetadata entity) {
        return false;
    }
}
