package yyc.open.framework.microants.components.kit.report;

import yyc.open.framework.microants.components.kit.report.commons.ReportEnums;

/**
 * {@link ReportCallback}
 *
 * @author <a href="mailto:siran0611@gmail.com">siran.yao</a>
 * @version ${project.version}
 * @date 2021/8/2
 */
public interface ReportCallback {
    void onReceived(String taskId, ReportEvent.EventType type);

    void onException(String taskId, String msg);
}
