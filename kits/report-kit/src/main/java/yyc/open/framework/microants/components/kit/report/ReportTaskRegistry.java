package yyc.open.framework.microants.components.kit.report;

import com.google.common.collect.Maps;
import org.apache.commons.collections4.CollectionUtils;
import yyc.open.framework.microants.components.kit.common.uuid.UUIDsKit;
import yyc.open.framework.microants.components.kit.common.validate.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * {@link ReportTaskRegistry}
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/7/30
 */
public class ReportTaskRegistry {
    private ReportStatus reportStatus;
    private Map<String, ReportTask> tasks;
    private Map<String, ReportTask> failTasks;

    private ReportTaskRegistry(ReportStatus reportStatus) {
        this.reportStatus = reportStatus;
        this.tasks = Maps.newConcurrentMap();
        this.failTasks = Maps.newConcurrentMap();
    }

    public enum ReportRegistryEnum {
        INSTANCE;

        ReportTaskRegistry reportRegistry;

        public ReportTaskRegistry getReportRegistry(ReportStatus reportStatus) {
            if (reportRegistry == null) {
                reportRegistry = new ReportTaskRegistry(reportStatus);
            }
            return reportRegistry;
        }
    }

    /**
     * Returns the task collections to support parallel execution.
     *
     * @param config need to generate report.
     * @param entity data need to generate report.
     * @return
     */
    public List<ReportTask> createTask(ReportConfig config, ReportMetadata entity) {
        List<ReportTask> tasks = new ArrayList<>();
        entity.getContent().getData().stream().forEach(data -> {
            data.stream().forEach(item -> {
                if (CollectionUtils.isEmpty(item.getTexts())
                        && CollectionUtils.isEmpty(item.getTables())) {

                    String taskId = UUIDsKit.base64UUID();
                    ReportTask task = ReportTask.builder()
                            .reportId(entity.getReportId())
                            .reportName(entity.getReportName())
                            .outputPath(config.getOutputPath())
                            .templatePath(item.getTemplateUrl())
                            .taskId(taskId)
                            .data(ReportTask.parseReportData(item))
                            .build();
                    task.setReportType(item.getType());

                    item.setTaskId(taskId);
                    tasks.add(task);
                }
            });
        });
        this.reportStatus.publishEvent(entity.getReportId(), ReportEvent.EventType.CREATION);
        return tasks;
    }

    /**
     * Add the task that
     *
     * @param taskId
     */
    public void addToFailQueue(@NonNull String taskId) {
        ReportTask failTask = this.tasks.remove(taskId);
        this.failTasks.put(taskId, failTask);
    }
}
