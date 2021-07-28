package yyc.open.framework.microants.components.starter.es.annotations;

import java.lang.annotation.*;

/**
 * {@link Index}
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/7/23
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface Index {
    String[] name() default "";
}
