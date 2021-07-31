package yyc.open.framework.microants.components.kit.report;

import com.google.common.collect.Maps;
import yyc.open.framework.microants.components.kit.common.validate.NonNull;
import yyc.open.framework.microants.components.kit.report.listener.Listener;

import java.util.Map;

/**
 * {@link ReportTaskRegistry}
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/7/30
 */
public class ReportTaskRegistry implements Listener {
    private Map<String, ReportTask> tasks = Maps.newConcurrentMap();
    private Map<String, ReportTask> failTasks = Maps.newConcurrentMap();

    enum ReportRegistryEnum {
        INSTANCE;

        ReportTaskRegistry reportRegistry;

        ReportTaskRegistry getReportRegistry() {
            if (reportRegistry == null) {
                reportRegistry = new ReportTaskRegistry();
            }
            return reportRegistry;
        }
    }

    @Override
    public void onSuccess(ReportEvent event) {

    }

    @Override
    public void onFailure(ReportEvent event) {

    }

    /**
     *
     * @param taskId
     */
    public void addToFailQueue(@NonNull String taskId) {
        ReportTask failTask = this.tasks.remove(taskId);
        this.failTasks.put(taskId, failTask);
    }
}
