package yyc.open.framework.microants.components.kit.report;

import com.google.common.collect.Maps;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.http.util.Asserts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import yyc.open.framework.microants.components.kit.common.validate.NonNull;
import yyc.open.framework.microants.components.kit.report.exceptions.ReportException;
import yyc.open.framework.microants.components.kit.report.handler.FileHandler;
import yyc.open.framework.microants.components.kit.report.handler.ReportHandlerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
                .getReportRegistry(status, config.getOutputPath());
    }

    /**
     * Convert html to pdf.
     *
     * @param htmlPath html path.
     */
    public void convertHTMLToPdf(String htmlPath) {
        try {
            FileHandler.convertToPdf(htmlPath, config);
        } catch (Exception e) {
            LOGGER.error("[Report] convert html to pdf failure {}", e);
        }
    }

    /**
     * Delete report all resource(image,report self and mapping in memory).
     *
     * @param reportId represent report identity id.
     */
    public void deleteReport(String reportId) {
        try {
            this.taskRegistry.deleteReport(reportId);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Generate the report that supports batch generation.
     */
    public Map<String, List<String>> generateReport(List<ReportMetadata> reportEntities) {
        return generate(reportEntities, false);
    }

    /**
     * TODO next version.
     * <p>
     * Generate the report, in additional supports parallel generation.
     */
    private Map<String, List<String>> generateReportParallel(List<ReportMetadata> reportEntities) {
        return generate(reportEntities, true);
    }


    private Map<String, List<String>> generate(List<ReportMetadata> reportEntities, boolean parallel) {
        // 1. Check report status whether running
        if (checkReportState()) {
            throw new ReportException("[Report Runtime] has not started, please start first.");
        }

        if (CollectionUtils.isEmpty(reportEntities)) {
            return null;
        }

        Map<String, List<String>> rest = Maps.newHashMapWithExpectedSize(reportEntities.size());

        // 2. Create the task collections.
        reportEntities.stream().forEach(entity -> {

            // Create report dir.
            File outputPath = new File(config.getOutputPath() + entity.getReportId() + "/");
            if (!outputPath.exists()) {
                outputPath.mkdirs();
            }
            entity.setPath(outputPath.getAbsolutePath());

            // Check report type whether is null (type is required parameter).
            Asserts.check(entity.getReportType() != null, "[Constructor Report] the report type is must, please setup before build.");

            List<ReportTask> tasks = taskRegistry.createTask(config, entity);
            if (CollectionUtils.isEmpty(tasks)) {
                // Notice：in situation that task collections is empty also needs to generation report.
                report(rest, entity, parallel);
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
                                    // todo move follow code into this.
                                    reportStatus.publishEvent(taskId, result, ReportEvent.EventType.PARTIALLY_COMPLETED);
                                    latch.countDown();
                                    // Add task result.
                                    entity.setSubTaskExecutionResult(taskId, result);
                                    taskRegistry.updateChecksum(entity.getReportId(), taskId, result);
                                    break;
                                case COMPLETED:
                                    reportStatus.publishEvent(taskId, ReportEvent.EventType.COMPLETED);
                                    break;
                            }
                        }

                        @Override
                        public void onException(String taskId, String msg) {
                            LOGGER.error("[Report] subTask-{}-{} execute failed.", entity.getReportId(), taskId);
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
                report(rest, entity, parallel);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        return rest;
    }

    private void report(Map<String, List<String>> rest, ReportMetadata metadata, boolean parallel) {
        // Start execute root task(report).
        reportStatus.publishEvent(metadata.getReportId(), ReportEvent.EventType.REPORT);
        this.handlerFactory.handle(metadata, parallel, new ReportCallback() {
            @Override
            public void onReceived(String taskId, String result, ReportEvent.EventType type) {
                reportStatus.publishEvent(taskId, ReportEvent.EventType.COMPLETED);
                List<String> value = rest.getOrDefault(metadata.getReportId(), new ArrayList<>());
                value.add(result);
                rest.put(metadata.getReportId(), value);
            }

            @Override
            public void onException(String id, String msg) {
                LOGGER.error("[Report] {} execute failed.", id);
                reportStatus.publishEvent(id, msg, ReportEvent.EventType.FAIL);
                // Add to fail queue that ready to re-execute.
                taskRegistry.addToFailQueue(id);
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

    void close() {
        LOGGER.info("[Report] start closing.");
        // Resource close.
        this.threadPool.shutdown();
        this.taskRegistry.close();
    }
}
