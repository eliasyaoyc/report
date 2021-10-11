package yyc.open.component.report;

import lombok.Builder;
import lombok.Getter;

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
	private ReportMetadata entity;

	public enum EventType {
		CREATION,
		PARTIALLY_COMPLETED,
		REPORT,
		COMPLETED,
		FAIL
	}
}
