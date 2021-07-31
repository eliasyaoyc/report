package yyc.open.framework.microants.components.kit.report.listener;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import yyc.open.framework.microants.components.kit.common.reflect.AnnotationScannerKit;
import yyc.open.framework.microants.components.kit.report.ReportEvent;
import yyc.open.framework.microants.components.kit.report.commons.Processor;

import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static yyc.open.framework.microants.components.kit.report.commons.ReportConstants.*;

/**
 * {@link ReportListenerFactory}
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/7/30
 */
public class ReportListenerFactory {
    private Map<String, Set<Listener>> listeners = Maps.newHashMap();

    private ReportListenerFactory() {
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

    public void onEvent(ReportEvent event) {
        switch (event.getType()) {
            case CREATION:
            case PARTIALLY_COMPLETED:
            case COMPLETED:
            case FAIL:
                this.listeners.get(ALARM_LISTENER).stream().forEach(listener -> listener.onFailure(event));
        }
    }

    public enum ReportListenerFactoryEnum {

        INSTANCE;

        private ReportListenerFactory reportListenerFactory;

        public ReportListenerFactory getReportListenerFactory() {
            if (reportListenerFactory == null) {
                reportListenerFactory = new ReportListenerFactory();
            }
            return reportListenerFactory;
        }
    }
}
