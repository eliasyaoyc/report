package yyc.open.component.report.commons.validate;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link ValidationException} Encapsulates an accumulation of validation errors.
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/8/2
 */
public class ValidationException extends IllegalArgumentException {
    private final List<String> validationErrors = new ArrayList<>();

    public ValidationException() {
        super("validation filed");
    }

    /**
     * Add a new validation error to the accumulating validation errors.
     *
     * @param error the error to add.
     */
    public final void addValidationError(String error) {
        validationErrors.add(error);
    }

    /**
     * Add a sequence of validation errors to the accumulating validation errors.
     *
     * @param errors the errors to add.
     */
    public final void addValidationErrors(Iterable<String> errors) {
        for (String error : errors) {
            validationErrors.add(error);
        }
    }

    /**
     * Returns the validation errors accumulated.
     *
     * @return
     */
    public final List<String> validationErrors() {
        return validationErrors;
    }

    @Override
    public String getMessage() {
        StringBuilder sb = new StringBuilder();
        sb.append("Validation Failed:");
        int index = 0;
        for (String validationError : validationErrors) {
            sb.append(++index).append(": ").append(validationError).append(";");
        }
        return sb.toString();
    }
}
