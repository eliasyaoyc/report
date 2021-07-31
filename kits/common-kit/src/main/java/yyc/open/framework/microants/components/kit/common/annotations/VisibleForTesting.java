package yyc.open.framework.microants.components.kit.common.annotations;

import java.lang.annotation.*;

/**
 * {@link VisibleForTesting} This is only open for test cases.
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/7/31
 */
@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.FIELD, ElementType.CONSTRUCTOR})
@Documented
public @interface VisibleForTesting {
}
