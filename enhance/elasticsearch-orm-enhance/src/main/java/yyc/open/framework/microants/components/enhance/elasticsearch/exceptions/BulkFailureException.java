package yyc.open.framework.microants.components.enhance.elasticsearch.exceptions;

import java.util.Map;

/**
 * {@link BulkFailureException}
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/7/23
 */
public class BulkFailureException extends ElasticsearchException {
    private static final long serialVersionUID = 5241692221150614145L;

    private final Map<String,String> failedDocuments;

    public BulkFailureException(String msg,Map<String, String> failedDocuments) {
        super(msg);
        this.failedDocuments = failedDocuments;
    }

    public Map<String, String> getFailedDocuments() {
        return failedDocuments;
    }
}
