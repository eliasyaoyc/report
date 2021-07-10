package yyc.open.framework.microants.components.starter.circuitbreaker.exception;

import jdk.nashorn.internal.objects.annotations.Getter;

/**
 * {@link MicroantsCircuitBreakerDirectThrowException}
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/7/10
 */
public class MicroantsCircuitBreakerDirectThrowException extends MicroantsCircuitBreakerException{

    private RuntimeException exception;

    /**
     * Constructs a new runtime exception with the specified detail message. The cause is not
     * initialized, and may subsequently be initialized by a call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for later retrieval by the
     *     {@link #getMessage()} method.
     */
    public MicroantsCircuitBreakerDirectThrowException(String message, RuntimeException exception) {
        super(message);
        this.exception = exception;
    }

    public RuntimeException getException() {
        return exception;
    }
}
