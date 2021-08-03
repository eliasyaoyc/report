package yyc.open.framework.microants.components.kit.report;

import lombok.Getter;

/**
 * {@link MatcherAndConverter}
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/8/3
 */
@Getter
public class MatcherAndConverter {
    private final Matcher matcher;
    private final Converter converter;

    public MatcherAndConverter(Matcher matcher, Converter converter) {
        this.matcher = matcher;
        this.converter = converter;
    }
}
