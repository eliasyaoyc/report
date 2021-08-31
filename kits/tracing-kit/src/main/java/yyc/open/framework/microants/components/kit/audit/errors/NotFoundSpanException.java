package yyc.open.framework.microants.components.kit.audit.errors;

/**
 * @author Elias (siran0611@gmail.com)
 */
public class NotFoundSpanException extends AuditException {

	private static final long serialVersionUID = -1766461994974821048L;

	public NotFoundSpanException() {
	}

	public NotFoundSpanException(String message) {
		super(message);
	}

	public NotFoundSpanException(String message, Throwable cause) {
		super(message, cause);
	}

	public NotFoundSpanException(Throwable cause) {
		super(cause);
	}

	public NotFoundSpanException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
