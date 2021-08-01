package yyc.open.framework.microants.components.kit.common.file;

import com.google.common.base.Strings;
import yyc.open.framework.microants.components.kit.common.validate.Asserts;

/**
 * {@link FileKit}
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/7/31
 */
public class FileKit {

    public static String suffix(CharSequence identifier, String path) {
        Asserts.hasText(path);
        return Strings.commonSuffix(path, identifier);
    }
}
