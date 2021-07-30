package yyc.open.framework.microants.components.kit.report.handler;

import yyc.open.framework.microants.components.kit.report.ReportEvent;
import yyc.open.framework.microants.components.kit.report.ReportInstanceProvider;
import yyc.open.framework.microants.components.kit.report.ReportTask;

import java.util.List;

/**
 * {@link ReportHandlerFactory}
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/7/30
 */
public class ReportHandlerFactory {

    public void handle(List<ReportTask> tasks, boolean parallel) {
        if (parallel) {
            handleParallel(tasks);
            return;
        }

        // Normal execution.

    }

    private void handleParallel(List<ReportTask> tasks) {

    }

    private void reportStatus() {
    }

    /**
     * The task about generate report that is completed.
     *
     * @param taskId The corresponding task id.
     */
    private void doFinish(String taskId) {
        ReportEvent event = ReportEvent.builder()
                .type(ReportEvent.EventType.FINISH)
                .taskId(taskId)
                .build();
        ReportInstanceProvider.listenerFactory().onEvent(event);
    }

    public enum HandlerFactoryEnum {
        INSTANCE;

        ReportHandlerFactory factory;

        public ReportHandlerFactory getReportHandlerFactory() {
            if (factory == null) {
                factory = new ReportHandlerFactory();
            }
            return factory;
        }
    }
}
