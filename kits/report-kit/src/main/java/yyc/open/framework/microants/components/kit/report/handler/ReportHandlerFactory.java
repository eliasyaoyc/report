package yyc.open.framework.microants.components.kit.report.handler;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import yyc.open.framework.microants.components.kit.common.reflect.AnnotationScannerKit;
import yyc.open.framework.microants.components.kit.common.validate.NonNull;
import yyc.open.framework.microants.components.kit.report.*;
import yyc.open.framework.microants.components.kit.report.commons.Processor;
import yyc.open.framework.microants.components.kit.report.commons.ReportEnums;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static yyc.open.framework.microants.components.kit.report.commons.ReportConstants.*;

/**
 * {@link ReportHandlerFactory}
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/7/30
 */
public class ReportHandlerFactory {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReportHandlerFactory.class);

    private ReportStatus reportStatus;
    private Map<String, Set<Handler>> handlers = Maps.newHashMap();

    private ReportHandlerFactory(@NonNull ReportStatus reportStatus) {
        this.reportStatus = reportStatus;

        // Construct handlers through processor annotation.
        Map<Class<? extends Annotation>, Set<Class<?>>> classSetMap = AnnotationScannerKit.scanClassesByAnnotations(ROOT_NAME, Processor.class);
        if (classSetMap.isEmpty()) {
            return;
        }
        for (Map.Entry<Class<? extends Annotation>, Set<Class<?>>> entity : classSetMap.entrySet()) {
            Processor processor = (Processor) entity.getKey().cast(Processor.class);
            if (!processor.type().equals(HANDLER)) {
                continue;
            }
            Set<Handler> values = entity.getValue().stream().map(val -> {
                Handler handler = (Handler) val.cast(Handler.class);
                return handler;
            }).collect(Collectors.toSet());

            handlers.getOrDefault(processor.name(), Sets.newHashSet()).addAll(values);
        }
    }

    /**
     * Execute tasks and there are two models, as follows:
     * Serial execution. In serial model, the input param `tasks` represent a report task，
     * so only one element in this collection.
     * But in parallel model，`tasks` is  a collection that represents a batch tasks.
     *
     * @param tasks
     * @param parallel whether parallel process.
     * @param callback handle the response when a task completed.
     */
    public void handle(@NonNull List<ReportTask> tasks, boolean parallel, ReportCallback callback) {
        // Handle error.
        if (handlers.isEmpty()) {
            String taskIds = tasks.stream().map(task -> task.getReportId()).collect(Collectors.joining());
            String msg = String.format("[ReportHandler] task {} handle failed, handlers do not initialized yet.", taskIds);
            LOGGER.error(msg);

            reportStatus.publishEvent(taskIds, msg, ReportEvent.EventType.FAIL);
        }

        // Parallel model.
        if (parallel) {
            handleParallel(tasks);
            return;
        }

        // Serial model todo consider that instead of use threadPool.
        tasks.stream().forEach(task -> {
            task.getChilds().stream().forEach(subTask -> {
                Set<Handler> handlers = chooseHandler(subTask.getType());

                handlers.stream().forEach(handler -> handler.onHandle(subTask, callback));
            });
            doFinish(task.getReportId());
        });
    }

    /**
     * TODO in next version.
     *
     * @param tasks
     */
    private void handleParallel(List<ReportTask> tasks) {

    }

    /**
     * Choose the handler corresponding to the task type.
     *
     * @return the handler.
     */
    private Set<Handler> chooseHandler(ReportEnums type) {
        return ReportEnums.isCharts(type) ? this.handlers.get(CHART_HANDLE) : this.handlers.get(FILE_HANDLE);
    }

    /**
     * The task about generate report that is completed.
     *
     * @param taskId The corresponding task id.
     */
    private void doFinish(String taskId) {
        LOGGER.info("[Report Handler] report {} generation success.", taskId);
        reportStatus.publishEvent(taskId, "", ReportEvent.EventType.COMPLETED);
    }

    public enum HandlerFactoryEnum {
        INSTANCE;

        ReportHandlerFactory factory;

        public ReportHandlerFactory getReportHandlerFactory(ReportStatus reportStatus) {
            if (factory == null) {
                factory = new ReportHandlerFactory(reportStatus);
            }
            return factory;
        }
    }
}
