package yyc.open.framework.microants.components.kit.report;

import com.google.common.collect.Maps;
import yyc.open.framework.microants.components.kit.common.validate.NonNull;
import yyc.open.framework.microants.components.kit.report.entity.ReportEntity;

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

    private ReportTaskRegistry() {
        this.reportStatus = ReportContextProvider.INSTANCE.getContext().getReportStatus();
    }

    /**
     * Returns the task collections to support parallel execution.
     *
     * @param config   need to generate report.
     * @param entities data need to generate report.
     * @return
     */
    public List<ReportTask> createTask(ReportConfig config, List<ReportEntity> entities) {
        this.reportStatus.publishEvent("", "", ReportEvent.EventType.CREATION);
        return null;
    }

    public enum ReportRegistryEnum {
        INSTANCE;

        ReportTaskRegistry reportRegistry;

        public ReportTaskRegistry getReportRegistry() {
            if (reportRegistry == null) {
                reportRegistry = new ReportTaskRegistry();
            }
            return reportRegistry;
        }
    }

    public ReportEntity getReportEntity(String taskId) {
        return null;
    }

    /**
     * @param taskId
     */
    public void addToFailQueue(@NonNull String taskId) {
        ReportTask failTask = this.tasks.remove(taskId);
        this.failTasks.put(taskId, failTask);
    }
}
