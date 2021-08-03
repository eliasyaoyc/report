package yyc.open.framework.microants.components.kit.common.beans;

/**
 * {@link Matcher} Returns {@code true} or {@code false} for a given input.
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/8/3
 */
public interface Matcher<T> {
    /**
     * Returns {@code true} if this matches {@code t}, {@code false} otherwise.
     */
    boolean matches(T t);

    /**
     * Returns a new matcher which returns {@code true} if both this and the
     * given matcher return {@code true}.
     */
    Matcher<T> and(Matcher<? super T> other);

    /**
     * Returns a new matcher which returns {@code true} if either this or the
     * given matcher return {@code true}.
     */
    Matcher<T> or(Matcher<? super T> other);
}
