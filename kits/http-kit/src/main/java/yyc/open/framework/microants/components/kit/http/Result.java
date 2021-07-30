package yyc.open.framework.microants.components.kit.http;

import lombok.Builder;

/**
 * {@link Result}
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/7/30
 */
@Builder
public class Result {
    private Integer code;
    private Object msg;
}
