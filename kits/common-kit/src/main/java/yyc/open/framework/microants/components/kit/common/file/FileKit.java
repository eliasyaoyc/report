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

    public static String suffix(String path, CharSequence flag) {
        Asserts.hasText(path);
        return Strings.commonSuffix(path, flag);
    }

    public static String[] splitWithSuffix(String path, CharSequence flag) {
        Asserts.hasText(path);
        String suffix = suffix(path, flag);
        return path.split(suffix);
    }
}
