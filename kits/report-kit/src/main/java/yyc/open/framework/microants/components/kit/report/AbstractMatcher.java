package yyc.open.framework.microants.components.kit.report;

/**
 * {@link AbstractMatcher}
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/8/3
 */
public abstract class AbstractMatcher<T> implements Matcher<T> {
    @Override
    public Matcher<T> and(Matcher<? super T> other) {
        return new AndMatcher<>(this, other);
    }

    @Override
    public Matcher<T> or(Matcher<? super T> other) {
        return new OrMatcher<>(this, other);
    }

    private static class AndMatcher<T> extends AbstractMatcher<T> {
        private final Matcher<? super T> a, b;

        public AndMatcher(Matcher<? super T> a, Matcher<? super T> b) {
            this.a = a;
            this.b = b;
        }

        @Override
        public boolean matches(T t) {
            return a.matches(t) && b.matches(t);
        }

        @Override
        public boolean equals(Object other) {
            return other instanceof AndMatcher
                    && ((AndMatcher<?>) other).a.equals(a)
                    && ((AndMatcher<?>) other).b.equals(b);
        }

        @Override
        public int hashCode() {
            return 41 * (a.hashCode() ^ b.hashCode());
        }

        @Override
        public String toString() {
            return "and(" + a + ", " + b + ")";
        }
    }

    private static class OrMatcher<T> extends AbstractMatcher<T> {
        private final Matcher<? super T> a, b;

        OrMatcher(Matcher<? super T> a, Matcher<? super T> b) {
            this.a = a;
            this.b = b;
        }

        @Override
        public boolean matches(T t) {
            return a.matches(t) || b.matches(t);
        }

        @Override
        public boolean equals(Object other) {
            return other instanceof OrMatcher
                    && ((OrMatcher<?>) other).a.equals(a)
                    && ((OrMatcher<?>) other).b.equals(b);
        }

        @Override
        public int hashCode() {
            return 37 * (a.hashCode() ^ b.hashCode());
        }

        @Override
        public String toString() {
            return "or(" + a + ", " + b + ")";
        }
    }
}
