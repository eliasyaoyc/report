package yyc.open.component.report.exceptions;

/**
 * {@link ReportException}
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/7/28
 */
public class ReportException extends RuntimeException {

	private static final long serialVersionUID = -4680761951462797309L;
	private String taskId;

	public ReportException() {
		super();
	}

	public ReportException(String message) {
		super(message);
	}

	public ReportException(String message, Throwable cause) {
		super(message, cause);
	}

	public ReportException(Throwable cause) {
		super(cause);
	}

	protected ReportException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public String getTaskId() {
		return taskId;
	}
}
