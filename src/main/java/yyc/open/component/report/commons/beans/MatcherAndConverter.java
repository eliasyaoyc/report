package yyc.open.component.report.commons.beans;


import java.util.Objects;
import lombok.Getter;
import org.apache.commons.lang3.reflect.TypeLiteral;

/**
 * {@link MatcherAndConverter}
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/8/3
 */
@Getter
public final class MatcherAndConverter {
    private final Matcher<? super TypeLiteral<?>> typeMatcher;
    private final TypeConverter typeConverter;

    public MatcherAndConverter(Matcher<? super TypeLiteral<?>> typeMatcher,
                               TypeConverter typeConverter) {
        this.typeMatcher = Objects.requireNonNull(typeMatcher, "type matcher");
        this.typeConverter = Objects.requireNonNull(typeConverter, "converter");
    }
}
