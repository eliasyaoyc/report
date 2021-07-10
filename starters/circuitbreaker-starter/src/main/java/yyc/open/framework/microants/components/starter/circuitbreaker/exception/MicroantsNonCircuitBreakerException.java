package yyc.open.framework.microants.components.starter.circuitbreaker.exception;

/**
 * {@link MicroantsNonCircuitBreakerException}
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/7/10
 */
public class MicroantsNonCircuitBreakerException extends RuntimeException{
    /**
     * Constructs a new runtime exception with the specified cause and a detail message of
     * <tt>(cause==null ? null : cause.toString())</tt> (which typically contains the class and detail
     * message of <tt>cause</tt>). This constructor is useful for runtime exceptions that are little
     * more than wrappers for other throwables.
     *
     * @param cause the cause (which is saved for later retrieval by the {@link #getCause()} method).
     *     (A <tt>null</tt> value is permitted, and indicates that the cause is nonexistent or
     *     unknown.)
     * @since 1.4
     */
    public MicroantsNonCircuitBreakerException(Throwable cause) {
        super(cause);
    }
}
