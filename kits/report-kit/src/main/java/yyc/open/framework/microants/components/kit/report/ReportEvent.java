package yyc.open.framework.microants.components.kit.report;

import lombok.Builder;
import lombok.Getter;
import yyc.open.framework.microants.components.kit.report.entity.ReportEntity;

/**
 * {@link ReportEvent}
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/7/30
 */
@Getter
@Builder
public class ReportEvent {
    private EventType type;
    private String message;
    private String taskId;
    private ReportEntity entity;

    public enum EventType {
        CREATION,
        PARTIALLY_COMPLETED,
        REPORT,
        COMPLETED,
        FAIL
    }
}
