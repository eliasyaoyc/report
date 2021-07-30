package yyc.open.framework.microants.components.kit.validate;

import java.lang.annotation.*;

/**
 * {@link NotNull}
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/7/30
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER, ElementType.FIELD})
@Documented
public @interface NotNull {
}
