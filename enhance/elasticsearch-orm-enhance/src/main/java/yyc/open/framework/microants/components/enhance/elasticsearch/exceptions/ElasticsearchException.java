package yyc.open.framework.microants.components.enhance.elasticsearch.exceptions;

/**
 * {@link ElasticsearchException}
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/7/22
 */
public class ElasticsearchException extends Exception{
    private static final long serialVersionUID = 8611433672948237689L;

    public ElasticsearchException() {
        super();
    }

    public ElasticsearchException(String message) {
        super(message);
    }

    public ElasticsearchException(String message, Throwable cause) {
        super(message, cause);
    }

    public ElasticsearchException(Throwable cause) {
        super(cause);
    }

    protected ElasticsearchException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
