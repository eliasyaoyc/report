package yyc.open.framework.microants.components.kit.report;

import org.apache.commons.lang3.reflect.TypeLiteral;

/**
 * {@link Converter}
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/8/3
 */
public interface Converter {
    Object convert(Object value, TypeLiteral<?> toType);
}
