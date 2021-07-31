package yyc.open.framework.microants.components.enhance.elasticsearch.exceptions;

/**
 * {@link ResourceFailureException}
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/7/23
 */
public class ResourceFailureException extends ElasticsearchException{
    private static final long serialVersionUID = 142109778642814029L;

    public ResourceFailureException() {
    }

    public ResourceFailureException(String message) {
        super(message);
    }

    public ResourceFailureException(String message, Throwable cause) {
        super(message, cause);
    }
}
