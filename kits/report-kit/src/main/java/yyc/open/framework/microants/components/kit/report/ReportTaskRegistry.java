package yyc.open.framework.microants.components.kit.report;

import com.google.common.collect.Maps;
import org.apache.commons.collections4.CollectionUtils;
import yyc.open.framework.microants.components.kit.common.beans.MatcherAndConverter;
import yyc.open.framework.microants.components.kit.common.beans.MatcherAndConverterKit;
import yyc.open.framework.microants.components.kit.common.uuid.UUIDsKit;
import yyc.open.framework.microants.components.kit.common.validate.NonNull;
import yyc.open.framework.microants.components.kit.report.entity.ReportData;
import yyc.open.framework.microants.components.kit.report.entity.ReportEntity;

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
    private Map<String, ReportTask> tasks = Maps.newConcurrentMap();
    private Map<String, ReportTask> failTasks = Maps.newConcurrentMap();
    private MatcherAndConverter converter;

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

    private ReportTaskRegistry(ReportStatus reportStatus) {
        this.reportStatus = reportStatus;
        this.converter = MatcherAndConverterKit.convert(ReportData.class, ReportTask.class);
    }

    /**
     * Returns the task collections to support parallel execution.
     *
     * @param config   need to generate report.
     * @param entities data need to generate report.
     * @return
     */
    public List<ReportTask> createTask(ReportConfig config, List<ReportEntity> entities) {
        List<ReportTask> tasks = new ArrayList<>();

        String taskId = UUIDsKit.base64UUID();
        entities.stream().forEach(entity -> {
            entity.getContent().getData().stream().forEach(data -> {
                data.stream().forEach(item -> {
                    if (CollectionUtils.isEmpty(item.getTexts())
                            && CollectionUtils.isEmpty(item.getTables())) {

                        ReportTask task = ReportTask.builder()
                                .reportId(entity.getReportId())
                                .reportName(entity.getReportName())
                                .outputPath(config.getOutputPath())
                                .taskId(taskId)
                                .type(item.getType())
                                .build();
                        tasks.add(task);
                    }
                });
            });
        });

        this.reportStatus.publishEvent(taskId, "", ReportEvent.EventType.CREATION);
        return null;
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
