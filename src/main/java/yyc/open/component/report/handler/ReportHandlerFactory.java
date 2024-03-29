package yyc.open.component.report.handler;


import static yyc.open.component.report.commons.ReportConstants.CHART_HANDLE;
import static yyc.open.component.report.commons.ReportConstants.CHART_SW_HANDLE;
import static yyc.open.component.report.commons.ReportConstants.FILE_HANDLE;

import com.google.common.collect.Maps;
import java.util.Map;
import java.util.ServiceLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import yyc.open.component.report.ReportCallback;
import yyc.open.component.report.ReportConfig;
import yyc.open.component.report.Task;
import yyc.open.component.report.commons.Processor;
import yyc.open.component.report.commons.ReportEnums;
import yyc.open.component.report.commons.validate.Asserts;
import yyc.open.component.report.commons.validate.NonNull;

/**
 * {@link ReportHandlerFactory}
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/7/30
 */
public class ReportHandlerFactory {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReportHandlerFactory.class);

    private Map<String, Handler> handlers = Maps.newHashMap();
    private final ReportConfig config;

    private ReportHandlerFactory(@NonNull ReportConfig config) {
        this.config = config;
        // Construct handlers through processor annotation.
        ServiceLoader<Handler> handlers = ServiceLoader.load(Handler.class);
        for (Handler handler : handlers) {
            Processor spi = handler.getClass().getAnnotation(Processor.class);
            if (spi != null) {
                String name = spi.name();
                if (this.handlers.containsKey(name)) {
                    throw new RuntimeException(
                            "The @Processor value(" + name + ") repeat, for class(" + handler.getClass() + ") and class(" + this.handlers.get(name).getClass() + ").");
                }
                this.handlers.put(name, handler);
            }
        }
    }

    /**
     * Execute tasks and there are two models, as follows:
     * Serial execution. In serial model, the input param `tasks` represent a report task，
     * so only one element in this collection.
     * But in parallel model，`tasks` is  a collection that represents a batch tasks.
     *
     * @param task     maybe ReportTask or ReportEntity.
     * @param parallel whether parallel process.
     * @param callback handle the response when a task completed.
     */
    public <T> void handle(@NonNull T task, boolean parallel, ReportCallback callback) {
        Asserts.notEmpty(this.handlers, "[ReportHandler] handlers do not initialized yet.");
        Asserts.isTrue(task instanceof Task, "[ReportHandler] invalid task.");

        // Parallel model.
        if (parallel) {
            handleParallel(task);
            return;
        }

        // Serial model todo consider that instead of use threadPool.
        Handler handler = chooseHandler(task);
        Asserts.notNull(handler, "[ReportHandler] handler is empty.");
        handler.onHandle(task, config, callback);
    }

    /**
     * TODO in next version.
     *
     * @param task
     */
    private <T> void handleParallel(T task) {

    }

    /**
     * Choose the handler corresponding to the task type.
     *
     * @return the handler.
     */
    private <T> Handler chooseHandler(T task) {
        Task t = (Task) task;
        if (!ReportEnums.isCharts(t.getReportType())){
            return this.handlers.get(FILE_HANDLE);
        }
        if (this.config.getSw()) {
            return this.handlers.get(CHART_SW_HANDLE);
        }

				return this.handlers.get(CHART_HANDLE);
    }

    public enum HandlerFactoryEnum {
        INSTANCE;

        ReportHandlerFactory factory;

        public ReportHandlerFactory getReportHandlerFactory(ReportConfig config) {
            if (factory == null) {
                factory = new ReportHandlerFactory(config);
            }
            return factory;
        }
    }
}
