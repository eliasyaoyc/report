package yyc.open.framework.microants.components.kit.compress;

import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * {@link CompressTester}
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/7/31
 */
public class CompressTester {

    /**
     * Zip解压缩
     */
    public static class ZipTester {

        public static void main(String[] args) {

            // 压缩包
            Compress compress =
                    Skinny.builder().decompressEncodeCharset("GBK").outputSiz(1024 * 4).type(Skinny.CompressType.ZIP).build().getCompress();

            // 测试压缩
//      File[] files = new File[]{new File("/xxx/xxx.json")};
//      compress.compress(files, new File("/xxx/xxx/xxx.zip"), false);

            // 显示文件列表
            List<String> files = compress.listFiles(new File("/Users/misselvexu/Library/Containers/com.tencent.WeWorkMac/Data/Documents/Profiles/D70325608ADE304890B7B14697FB4010/Caches/Files/2021-04/57cf8ca21ac67b623e14c033f7e42772/星佑3.4.zip"));

            System.out.println(Arrays.toString(files.toArray()));
            // 测试解压
//      compress.decompress(new File("/Users/misselvexu/Library/Containers/com.tencent.WeWorkMac/Data/Documents/Profiles/D70325608ADE304890B7B14697FB4010/Caches/Files/2021-04/57cf8ca21ac67b623e14c033f7e42772/星佑3.4.zip")
//          , "/Users/misselvexu/Library/Containers/com.tencent.WeWorkMac/Data/Documents/Profiles/D70325608ADE304890B7B14697FB4010/Caches/Files/2021-04/57cf8ca21ac67b623e14c033f7e42772/星佑3.4");

        }

    }

    /**
     * 7z解压缩
     */
    public static class X7ZTester {

        public static void main(String[] args) {

            // 压缩包
            Compress compress =
                    Skinny.builder().outputSiz(1024 * 4).type(Skinny.CompressType.SEVENZ).build().getCompress();

            // 测试压缩
            File[] files = new File[]{new File("/xxx/xxx.json")};
            compress.compress(files, new File("/xxx/xxx/xxx.7z"), false);

            // 测试解压
            compress.decompress(new File("/xxx/xxx/xxx.7z"), "/xxx/xxx/xxx.json");

        }

    }

    /**
     * RAR5解压
     */
    public static class RAR5Tester {

        public static void main(String[] args) {

            // 压缩包
            Compress compress =
                    Skinny.builder().outputSiz(1024 * 4).type(Skinny.CompressType.RAR5).ignoreFolder(true).build().getCompress();

            compress.listFiles(new File("/Users/eliasyao/Desktop/asa.rar"));
            // 测试解压
            compress.decompress(new File("/Users/eliasyao/Desktop/asa.rar"), "/Users/eliasyao/Desktop/temp");

        }

    }


    /**
     * pdf解压缩
     */
    public static class PdfTester {

        public static void main(String[] args) {

            // 压缩包
            Compress compress =
                    Skinny.builder().outputSiz(1024 * 4).type(Skinny.CompressType.PDF).build().getCompress();

            // 测试压缩pdf
            File[] files = new File[]{new File("/xxx/xxx.pdf")};
            compress.compress(files, new File("/xxx/xxx/xxx.pdf"), false);

        }

    }

}
