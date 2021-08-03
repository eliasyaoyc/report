package yyc.open.framework.microants.components.kit.common.beans;


import org.apache.commons.lang3.reflect.TypeLiteral;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 * {@link MatcherAndConverterKit}
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/8/3
 */
public final class MatcherAndConverterKit {
    public static <S, D> MatcherAndConverter convert(Class<S> source, final Class<D> wrapperType) {
        try {
            final Method parser = wrapperType.getMethod(
                    "parseReportData", source.getClass());
            TypeConverter converter = new TypeConverter() {
                @Override
                public Object convert(Object value, TypeLiteral<?> toType) {
                    try {
                        return parser.invoke(null, value);
                    } catch (IllegalAccessException e) {
                        throw new AssertionError(e);
                    } catch (InvocationTargetException e) {
                        throw new RuntimeException(e.getTargetException());
                    }
                }
            };
            return convertToClass(wrapperType, converter);
        } catch (NoSuchMethodException e) {
            throw new AssertionError(e);
        }
    }

    public static <T> MatcherAndConverter convertToClass(Class<T> type, TypeConverter converter) {
        return convertToClasses(Matchers.identicalTo(type), converter);
    }

    public static MatcherAndConverter convertToClasses(final Matcher<? super Class<?>> matcher, TypeConverter converter) {
        return internalConvertToTypes(new AbstractMatcher<TypeLiteral<?>>() {
            @Override
            public boolean matches(TypeLiteral<?> typeLiteral) {
                Type type = typeLiteral.getType();
                if (!(type instanceof Class)) {
                    return false;
                }
                Class<?> clazz = (Class<?>) type;
                return matcher.matches(clazz);
            }
        }, converter);
    }

    private static MatcherAndConverter internalConvertToTypes(Matcher<? super TypeLiteral<?>> matcher,
                                                              TypeConverter converter) {
        return new MatcherAndConverter(matcher, converter);
    }
}
