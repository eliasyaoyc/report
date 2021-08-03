package yyc.open.framework.microants.components.kit.report;

/**
 * {@link Matcher}
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/8/3
 */
public interface Matcher<T> {
    boolean matches(T t);

    Matcher<T> and(Matcher<? super T> other);

    Matcher<T> or(Matcher<? super T> other);
}
