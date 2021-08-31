package yyc.open.framework.microants.components.kit.audit.errors;

/**
 * @author Elias (siran0611@gmail.com)
 */
public class AuditException extends RuntimeException {

	private static final long serialVersionUID = 8107696450483974887L;

	public AuditException() {
	}

	public AuditException(String message) {
		super(message);
	}

	public AuditException(String message, Throwable cause) {
		super(message, cause);
	}

	public AuditException(Throwable cause) {
		super(cause);
	}

	public AuditException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
