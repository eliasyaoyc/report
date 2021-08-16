package yyc.open.framework.microants.components.kit.common.file;

import com.google.common.base.Strings;
import yyc.open.framework.microants.components.kit.common.validate.Asserts;

import java.io.*;

/**
 * {@link FileKit}
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/7/31
 */
public class FileKit {

    public static String suffix(String path, Character flag) {
        Asserts.hasText(path);
        return Strings.commonSuffix(path, flag.toString());
    }

    public static String[] splitWithSuffix(String path, Character flag) {
        Asserts.hasText(path);
        int i = path.lastIndexOf(flag);
        return new String[]{path.substring(0, i), path.substring(i + 1)};
    }

    /**
     * Returns the resource path.
     *
     * @param path
     * @return
     */
    public static String getResourcePath(String path) {
        String p = Thread.currentThread().getContextClassLoader().getResource(path).getPath();
        boolean windows = System.getProperty("os.name").contains("Windows");
        return windows ? p.substring(1) : p;
    }

    /**
     * Create directory through current relative path.
     *
     * @param directory need to created directory.
     */
    public static void createPath(String directory) {
        File file = new File(directory);
        if (file.isDirectory()) {
            if (!file.exists()) {
                file.mkdirs();
            }
        } else {
            String newDir = directory.startsWith("/") ? directory : "/" + directory;
            new File(System.getProperty("user.dir") + newDir).mkdirs();
        }
    }

    public static String readFileAsStream(String path) throws IOException {
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        char[] buff = new char[inputStream.available()];
        bufferedReader.read(buff, 0, inputStream.available());
        return new String(buff);
    }
}
