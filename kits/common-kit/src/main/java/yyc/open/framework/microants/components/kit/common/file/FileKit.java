package yyc.open.framework.microants.components.kit.common.file;

import com.google.common.collect.Maps;
import org.apache.commons.io.IOUtils;
import sun.misc.BASE64Encoder;
import yyc.open.framework.microants.components.kit.common.validate.Asserts;

import java.io.*;
import java.util.Map;

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

    public static String getBase64FromPath(String path) {
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
        return getBase64FromInputStream(inputStream);
    }

    public static String getBase64FromInputStream(String path) {
        try {
            FileInputStream fileInputStream = new FileInputStream(path);
            return getBase64FromInputStream(fileInputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getBase64FromInputStream(InputStream in) {
        byte[] data = null;
        try {
            ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
            byte[] buff = new byte[100];
            int rc = 0;
            while ((rc = in.read(buff, 0, 100)) > 0) {
                swapStream.write(buff, 0, rc);
            }
            data = swapStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        BASE64Encoder encoder = new BASE64Encoder();
        return "data:img/jpg;base64," + encoder.encode(data);
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

    public static String tempFile(String sourceFile, String targetPath) throws IOException {
        return tempFile(sourceFile, targetPath, false);
    }

    public static String tempFile(String sourceFile, String targetPath, boolean repeated) throws IOException {
        InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream(sourceFile);
        String name = FilenameKit.getName(sourceFile);

        String tempPath = targetPath.endsWith(File.separator) ?
                targetPath + name :
                targetPath + File.separator + name;

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


    public static String tempPath() {
        String tempPath = System.getProperty("java.io.tmpdir").endsWith(File.separator) ?
                System.getProperty("java.io.tmpdir") :
                System.getProperty("java.io.tmpdir") + File.separator;
        return tempPath;
    }

    public static String createDir(String path) {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        return path;
    }

    public static void deleteFile(String path) throws IOException {
        if (!new File(path).exists()) {
            return;
        }
        if (System.getProperty("os.name").contains("Windows")) {
            Runtime.getRuntime().exec("del " + path);
        } else {
            Runtime.getRuntime().exec("rm -rf " + path);
        }
    }

    public static void main(String[] args) throws IOException {
//        Map<String, String> load = loadPackage("/Users/eliasyao/Desktop/raft-demo");
//        System.out.println(load);
//        FileInputStream fileInputStream = new FileInputStream("/var/folders/5p/p31bwvsj21jckn1b_j26wrpc0000gn/T/img_background.png");
//        String base64FromPath = getBase64FromInputStream(fileInputStream);
//        System.out.println(base64FromPath);

        File file = new File("/Users/eliasyao/Desktop/nta-parent/reports/1qjTWHsBgqP5XtlRekEG/1qjTWHsBgqP5XtlRekEG.pdf");
        boolean exists = file.exists();
        System.out.println(exists);
    }

    public static Map<String, String> loadPackage(String path) {
        Map<String, String> f = Maps.newHashMap();
        innerLoad(f, path);
        return f;
    }

    private static void innerLoad(Map<String, String> f, String path) {
        File file = new File(path);
        if (file.exists() && file.isDirectory()) {
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()) {
                    f.putIfAbsent(files[i].getName(), files[i].getAbsolutePath());
                    innerLoad(f, files[i].getAbsolutePath());
                }
            }
        }
    }
}
