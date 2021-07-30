package yyc.open.framework.microants.components.kit.report.listener;


import com.google.common.collect.Maps;
import yyc.open.framework.microants.components.kit.report.ReportEvent;

import java.util.Map;

/**
 * {@link ReportListenerFactory}
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/7/30
 */
public class ReportListenerFactory {
    private Map<String, Listener> listeners = Maps.newHashMap();

    private ReportListenerFactory() {

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
