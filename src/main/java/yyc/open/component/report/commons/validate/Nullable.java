package yyc.open.component.report.commons.validate;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.annotation.Nonnull;
import javax.annotation.meta.TypeQualifierNickname;
import javax.annotation.meta.When;

/**
 * {@link Nullable} A common Spring annotation to declare that annotated elements can be {@code null} under some
 * circumstance. Leverages JSR 305 meta-annotations to indicate nullability in Java to common tools
 * with JSR 305 support and used by Kotlin to infer nullability of Spring API.
 *
 * <p>Should be used at parameter, return value, and field level. Methods override should repeat
 * parent {@code @Nullable} annotations unless they behave differently.
 *
 * <p>Can be used in association with {@code @NonNullApi} or {@code @NonNullFields} to override the
 * default non-nullable semantic to nullable.
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/7/31
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.CONSTRUCTOR})
@Documented
@Nonnull(when = When.MAYBE)
@TypeQualifierNickname
public @interface Nullable {
}
