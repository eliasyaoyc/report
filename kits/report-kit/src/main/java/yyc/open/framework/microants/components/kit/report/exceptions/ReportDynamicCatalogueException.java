package yyc.open.framework.microants.components.kit.report.exceptions;

/**
 * {@link ReportDynamicCatalogueException}
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/8/12
 */
public class ReportDynamicCatalogueException extends ReportException {
    private static final long serialVersionUID = -1929270000735940325L;

    public ReportDynamicCatalogueException() {
    }

    public ReportDynamicCatalogueException(String message) {
        super(message);
    }

    public ReportDynamicCatalogueException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReportDynamicCatalogueException(Throwable cause) {
        super(cause);
    }

    public ReportDynamicCatalogueException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
