package yyc.open.framework.microants.components.kit.common.annotations;

import java.lang.annotation.*;

/**
 * {@link Unstable} Represents that the API in this class is not stable and may change.
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/7/31
 */
@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.FIELD, ElementType.CONSTRUCTOR})
@Documented
public @interface Unstable {
}
