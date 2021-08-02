package yyc.open.framework.microants.components.kit.report;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import yyc.open.framework.microants.components.kit.common.reflect.AnnotationScannerKit;
import yyc.open.framework.microants.components.kit.report.commons.Processor;
import yyc.open.framework.microants.components.kit.report.listener.Listener;

import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static yyc.open.framework.microants.components.kit.report.commons.ReportConstants.*;

/**
 * {@link ReportStatus}
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/8/2
 */
public class ReportStatus {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReportStatus.class);

    private Map<String, Set<Listener>> listeners = Maps.newHashMap();

    public ReportStatus() {
        // Construct listeners through processor annotation.
        Map<Class<? extends Annotation>, Set<Class<?>>> classSetMap = AnnotationScannerKit.scanClassesByAnnotations(ROOT_NAME, Processor.class);
        if (classSetMap.isEmpty()) {
            return;
        }
        for (Map.Entry<Class<? extends Annotation>, Set<Class<?>>> entity : classSetMap.entrySet()) {
            Processor processor = (Processor) entity.getKey().cast(Processor.class);
            if (!processor.type().equals(LISTENER)) {
                continue;
            }
            Set<Listener> values = entity.getValue().stream().map(val -> {
                Listener listener = (Listener) val.cast(Listener.class);
                return listener;
            }).collect(Collectors.toSet());

            listeners.getOrDefault(processor.name(), Sets.newHashSet()).addAll(values);
        }
    }

    /**
     * Publish the event that will be consumed by {@link Listener}
     *
     * @param taskId
     * @param msg
     * @param type
     */
    public void publishEvent(String taskId, String msg, ReportEvent.EventType type) {
        ReportEvent event = ReportEvent.builder()
                .taskId(taskId)
                .type(type)
                .message(msg)
                .build();

        switch (type) {
            case CREATION:
            case PARTIALLY_COMPLETED:
            case COMPLETED:
            case FAIL:
                LOGGER.info("[Default Listener] task {} execute fail, add to fail-queue and wait to retry", event.getTaskId());
                this.listeners.get(ALARM_LISTENER).stream().forEach(listener -> listener.onFailure(event));
        }
    }

    public enum ReportStatusEnum {
        INSTANCE;

        ReportStatus reportStatus;

        public ReportStatus getReportStatus() {
            if (reportStatus == null) {
                reportStatus = new ReportStatus();
            }
            return reportStatus;
        }
    }
}
