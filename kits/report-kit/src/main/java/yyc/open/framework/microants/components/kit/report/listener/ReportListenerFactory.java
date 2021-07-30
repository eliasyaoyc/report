package yyc.open.framework.microants.components.kit.report.listener;


import com.google.common.collect.Maps;
import yyc.open.framework.microants.components.kit.reflect.AnnotationScannerKit;
import yyc.open.framework.microants.components.kit.report.ReportEvent;
import yyc.open.framework.microants.components.kit.report.commons.Processor;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

import static yyc.open.framework.microants.components.kit.report.commons.ReportConstants.LISTENER;
import static yyc.open.framework.microants.components.kit.report.commons.ReportConstants.ROOT_NAME;

/**
 * {@link ReportListenerFactory}
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/7/30
 */
public class ReportListenerFactory {
    private Map<String, Listener> listeners = Maps.newHashMap();

    private ReportListenerFactory() {
        // Construct listeners through annotation.
        Map<Class<? extends Annotation>, Set<Class<?>>> classSetMap = AnnotationScannerKit.scanClassesByAnnotations(ROOT_NAME, AnnotationScannerKit.getPackagePath(ROOT_NAME), true, Arrays.asList(Processor.class));
        if (classSetMap.isEmpty()) {
            return;
        }
        classSetMap.entrySet().stream().filter(entity -> {
            Processor processor = (Processor) entity.getKey().cast(Processor.class);
            return processor.name().equals(LISTENER);
        });
    }

    public void onEvent(ReportEvent event) {
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
