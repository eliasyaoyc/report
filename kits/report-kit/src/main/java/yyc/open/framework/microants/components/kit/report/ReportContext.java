package yyc.open.framework.microants.components.kit.report;

import lombok.Builder;
import yyc.open.framework.microants.components.kit.report.ReportConfig.*;
import yyc.open.framework.microants.components.kit.report.listener.Listener;

import java.util.List;

/**
 * {@link ReportContext}
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/7/28
 */
@Builder
public class ReportContext {
    private GlobalConfig globalConfig;
    private ReportStatus reportStatus;
    private List<Listener> listeners;


    static class ReportStatus {

    }
}
