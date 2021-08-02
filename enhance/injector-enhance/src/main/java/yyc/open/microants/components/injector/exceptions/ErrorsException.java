package yyc.open.microants.components.injector.exceptions;

import yyc.open.microants.components.injector.Errors;

/**
 * {@link ErrorsException} Indicates that a result could not be returned while preparing or resolving a binding.
 * The caller should {@link Errors#merge(Errors) merge} the errors from this exception with their exsiting errors.
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/8/2
 */
public class ErrorsException extends Exception {
    private static final long serialVersionUID = 3818456929023864131L;

    private final Errors errors;

    public ErrorsException(Errors errors) {
        this.errors = errors;
    }

    public Errors getErrors() {
        return errors;
    }
}
