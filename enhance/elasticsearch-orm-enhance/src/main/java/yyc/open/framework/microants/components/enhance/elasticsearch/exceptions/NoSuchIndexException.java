package yyc.open.framework.microants.components.enhance.elasticsearch.exceptions;

/**
 * {@link NoSuchIndexException}
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/7/23
 */
public class NoSuchIndexException extends ElasticsearchException {
    private static final long serialVersionUID = -4266986021380927178L;

    private final String index;

    public NoSuchIndexException(String index, Throwable cause) {
        super(String.format("Index %s not found.", index), cause);
        this.index = index;
    }

    public String getIndex() {
        return index;
    }
}
