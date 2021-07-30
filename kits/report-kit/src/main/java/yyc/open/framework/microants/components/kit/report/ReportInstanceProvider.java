package yyc.open.framework.microants.components.kit.report;

import yyc.open.framework.microants.components.kit.report.handler.ReportHandlerFactory;
import yyc.open.framework.microants.components.kit.report.listener.ReportListenerFactory;

/**
 * {@link ReportInstanceProvider}
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/7/30
 */
public enum ReportInstanceProvider {
    ;

    public static ReportListenerFactory listenerFactory() {
        return ReportListenerFactory
                .ReportListenerFactoryEnum
                .INSTANCE
                .getReportListenerFactory();
    }

    public static ReportHandlerFactory handlerFactory() {
        return ReportHandlerFactory
                .HandlerFactoryEnum
                .INSTANCE
                .getReportHandlerFactory();
    }

    public static ReportTaskRegistry reportRegistry() {
        return ReportTaskRegistry
                .ReportRegistryEnum
                .INSTANCE
                .getReportRegistry();
    }
}
