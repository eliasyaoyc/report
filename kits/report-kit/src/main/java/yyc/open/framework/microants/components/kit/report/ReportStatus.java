package yyc.open.framework.microants.components.kit.report;

import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import yyc.open.framework.microants.components.kit.report.commons.Processor;
import yyc.open.framework.microants.components.kit.report.listener.Listener;

import java.util.Map;
import java.util.ServiceLoader;

import static yyc.open.framework.microants.components.kit.report.commons.ReportConstants.ALARM_LISTENER;

/**
 * {@link ReportStatus}
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/8/2
 */
public class ReportStatus {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReportStatus.class);

    private Map<String, Listener> listeners = Maps.newHashMap();

    public ReportStatus() {
        // Construct listeners through processor annotation.
//        Map<Class<? extends Annotation>, Set<Class<?>>> classSetMap = AnnotationScannerKit.scanClassesByAnnotations(ROOT_NAME, Processor.class);
//        if (classSetMap.isEmpty()) {
//            return;
//        }
//        for (Map.Entry<Class<? extends Annotation>, Set<Class<?>>> entity : classSetMap.entrySet()) {
//            Processor processor = (Processor) entity.getKey().cast(Processor.class);
//            if (!processor.type().equals(LISTENER)) {
//                continue;
//            }
//            Set<Listener> values = entity.getValue().stream().map(val -> {
//                Listener listener = (Listener) val.cast(Listener.class);
//                return listener;
//            }).collect(Collectors.toSet());
//
//            listeners.getOrDefault(processor.name(), Sets.newHashSet()).addAll(values);
//        }
        ServiceLoader<Listener> listeners = ServiceLoader.load(Listener.class);
        for (Listener listener : listeners) {
            Processor spi = listener.getClass().getAnnotation(Processor.class);
            if (spi != null) {
                String name = spi.name();
                if (this.listeners.containsKey(name)) {
                    throw new RuntimeException(
                            "The @Processor value(" + name + ") repeat, for class(" + listener.getClass() + ") and class(" + this.listeners.get(name).getClass() + ").");
                }
                this.listeners.put(name, listener);
            }
        }
    }

    /**
     * Publish the event that will be consumed by {@link Listener}
     *
     * @param id   Maybe ReportId or TaskId.
     * @param type
     */

    public void publishEvent(String id, ReportEvent.EventType type) {
        this.publishEvent(id, "", type);
    }

    public void publishEvent(String id, String msg, ReportEvent.EventType type) {
        ReportEvent event = ReportEvent.builder()
                .taskId(id)
                .type(type)
                .message(msg)
                .build();

        switch (type) {
            case CREATION:
                LOGGER.info("[ReportStatus] report {} all subTask are all creation.", id);
                break;
            case PARTIALLY_COMPLETED:
                LOGGER.info("[ReportStatus] task {} is completed.", id);
                break;
            case REPORT:
                LOGGER.info("[ReportStatus] report {} subTask are all finished.", id);

                break;
            case COMPLETED:
                LOGGER.info("[ReportStatus] report {} is generated completely", id);
                break;
            case FAIL:
                LOGGER.info("[ReportStatus] task {} execute fail, add to fail-queue and wait to retry", event.getTaskId());
                this.listeners.get(ALARM_LISTENER).onFailure(event);
                break;
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
