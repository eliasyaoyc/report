package yyc.open.framework.microants.components.kit.report.commons;

import java.lang.annotation.*;

/**
 * {@link Processor}
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/7/30
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface Processor {

    String name();

    String type();
}
