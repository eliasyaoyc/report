package yyc.open.framework.microants.components.kit.report;

/**
 * {@link ReportStatus}
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/8/2
 */
public class ReportStatus {

    public void reportStatus(String taskId, String msg, ReportEvent.EventType type) {
        ReportEvent event = ReportEvent.builder()
                .taskId(taskId)
                .type(type)
                .message(msg)
                .build();
        ReportContext.listenerFactory().onEvent(event);
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
