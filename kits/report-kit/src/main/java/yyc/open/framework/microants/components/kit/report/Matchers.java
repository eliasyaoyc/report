package yyc.open.framework.microants.components.kit.report;

import java.util.Objects;

/**
 * {@link Matchers}
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/8/3
 */
public class Matchers {
    /**
     * Returns a matcher which matches only the given object.
     */
    public static Matcher<Object> identicalTo(final Object value) {
        return new IdenticalTo(value);
    }

    private static class IdenticalTo extends AbstractMatcher<Object> {
        private final Object value;

        IdenticalTo(Object value) {
            this.value = Objects.requireNonNull(value, "value");
        }

        @Override
        public boolean matches(Object other) {
            return value == other;
        }

        @Override
        public boolean equals(Object other) {
            return other instanceof IdenticalTo
                    && ((IdenticalTo) other).value == value;
        }

        @Override
        public int hashCode() {
            return 37 * System.identityHashCode(value);
        }

        @Override
        public String toString() {
            return "identicalTo(" + value + ")";
        }
    }
}
