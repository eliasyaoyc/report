package yyc.open.framework.microants.components.kit.audit.errors;

/**
 * @author Elias (siran0611@gmail.com)
 */
public class AuditTooBusyException extends RuntimeException {

	private static final long serialVersionUID = 2797038246605354112L;

	public AuditTooBusyException() {
	}

	public AuditTooBusyException(String message) {
		super(message);
	}

	public AuditTooBusyException(String message, Throwable cause) {
		super(message, cause);
	}

	public AuditTooBusyException(Throwable cause) {
		super(cause);
	}

	public AuditTooBusyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
