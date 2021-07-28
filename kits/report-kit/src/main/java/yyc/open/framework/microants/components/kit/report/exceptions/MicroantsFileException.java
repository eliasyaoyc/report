package yyc.open.framework.microants.components.kit.report.exceptions;

/**
 * {@link MicroantsFileException}
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/7/28
 */
public class MicroantsFileException extends RuntimeException {
    private static final long serialVersionUID = -4680761951462797309L;

    public MicroantsFileException() {
        super();
    }

    public MicroantsFileException(String message) {
        super(message);
    }

    public MicroantsFileException(String message, Throwable cause) {
        super(message, cause);
    }

    public MicroantsFileException(Throwable cause) {
        super(cause);
    }

    protected MicroantsFileException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
