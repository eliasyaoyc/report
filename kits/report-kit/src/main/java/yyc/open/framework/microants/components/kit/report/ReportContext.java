package yyc.open.framework.microants.components.kit.report;

import lombok.Builder;
import lombok.Getter;
import yyc.open.framework.microants.components.kit.report.ReportConfig.GlobalConfig;
import yyc.open.framework.microants.components.kit.report.handler.ReportHandlerFactory;
import yyc.open.framework.microants.components.kit.report.listener.ReportListenerFactory;

/**
 * {@link ReportContext}
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/7/28
 */
@Getter
@Builder
public class ReportContext {
    private GlobalConfig globalConfig;
    private ReportStatus reportStatus;


    static class ReportStatus {

    }

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
