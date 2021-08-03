package yyc.open.framework.microants.components.kit.common.beans;

import org.apache.commons.lang3.reflect.TypeLiteral;

/**
 * {@link TypeConverter} Converts constant object values to a different type.
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/8/3
 */
public interface TypeConverter {

    /**
     * Converts a string value. Throws an exception if a conversion error occurs.
     */
    Object convert(Object value, TypeLiteral<?> toType);
}
