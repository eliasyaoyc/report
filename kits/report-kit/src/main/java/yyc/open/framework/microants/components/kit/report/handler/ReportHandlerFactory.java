package yyc.open.framework.microants.components.kit.report.handler;

import yyc.open.framework.microants.components.kit.report.ReportTask;

import java.util.List;

/**
 * {@link ReportHandlerFactory}
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/7/30
 */
public class ReportHandlerFactory {

    public void handle(List<ReportTask> tasks) {

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
