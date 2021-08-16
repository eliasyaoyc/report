package yyc.open.framework.microants.components.kit.common.file;

import org.apache.commons.io.IOUtils;
import yyc.open.framework.microants.components.kit.common.validate.Asserts;

import java.io.*;

/**
 * {@link FileKit}
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/7/31
 */
public class FileKit {

    public static String suffix(String path) {
        return suffix(path, '.');
    }

    public static String suffix(String path, Character flag) {
        Asserts.hasText(path);
        int i = path.lastIndexOf(flag);
        if (i <= 0) {
            return path;
        }
        return path.substring(i);
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
            String newDir = directory.startsWith(File.separator) ? directory : File.separator + directory;
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

    /**
     * Returns the temp file through source file that avoid can't read file from jar.
     *
     * @param sourceFile the source file.
     * @param repeated   whether repeated generation.
     * @return the temp fil.
     */
    public static String tempFile(String sourceFile, boolean repeated) throws IOException {
        InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream(sourceFile);
        String name = FilenameKit.getName(sourceFile);

        String tempPath = System.getProperty("java.io.tmpdir").endsWith(File.separator) ?
                System.getProperty("java.io.tmpdir") + name :
                System.getProperty("java.io.tmpdir") + File.separator + name;

        File tempFile = new File(tempPath);
        if (tempFile.exists() && !repeated) {
            return tempFile.getAbsolutePath();
        }
        IOUtils.copy(input, new FileOutputStream(tempFile));
        String absolutePath = tempFile.getAbsolutePath();
        // Resolve window path issues.
        if (absolutePath.startsWith(File.separator) && System.getProperty("os.name").contains("Windows")) {
            absolutePath.substring(1);
        }
        return absolutePath;
    }

    public static String tempFile(String sourceFile) throws IOException {
        return tempFile(sourceFile, false);
    }

    public static void main(String[] args) {
        InputStream resourceAsStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("/Users/eliasyao/Desktop/microants-components/kits/report-kit/target/classes/js/echarts-util.js");
        System.out.println(resourceAsStream);
    }
}
