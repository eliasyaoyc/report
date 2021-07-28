package yyc.open.framework.microants.components.kit.report;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import yyc.open.framework.microants.components.kit.report.exceptions.ReportException;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;

import static sun.tools.jconsole.Messages.WINDOWS;

/**
 * {@link ReportRuntime}
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/7/28
 */
public class ReportRuntime {
    private static final Logger logger = LoggerFactory.getLogger(ReportRuntime.class);

    private static final String EXEC_PATH_PREFIX = "exec/";
    private static final int PORT = 6666;
    private AtomicBoolean running;
    private ReportContext reportContext;

    {
        this.running = new AtomicBoolean(false);
    }


    enum Platforms {
        WINDOWS("Windows", ""),
        MACOS("Mac OS X", ""),
        UNIX("Unix", "");

        String name;
        String path;

        Platforms(String name, String path) {
            this.name = name;
            this.path = path;
        }

        /**
         * Returns the corresponding platform through input parma.
         *
         * @param name
         * @return
         */
        static Platforms getPlatforms(String name) {
            for (Platforms pf : Platforms.values()) {
                if (name.equals(pf.getName())) {
                    return pf;
                }
            }
            throw new IllegalArgumentException("");
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }
    }

    /**
     * Start the phantomjs plugin.
     */
    public void start() {
        String property = System.getProperty("os.name");
        Platforms platforms = Platforms.getPlatforms(property);
        String command = new StringBuilder(EXEC_PATH_PREFIX)
                .append(getResourcePath(platforms.getPath()))
                .append(" ")
                .append("js/echarts-util.js")
                .append(" -s -p ")
                .append(PORT).toString();
        try {
            Runtime.getRuntime().exec(command);
        } catch (IOException e) {
            throw new ReportException(String.format("[Report Runtime] exec command: %s fail", command), e);
        }
    }

    /**
     * Returns report runtime status, true if is running.
     *
     * @return
     */
    public boolean isStart() {
        return running.get();
    }

    /**
     * Stop the phantomjs plugin.
     */
    public void close() {
        HashMap<String, Object> pa = new HashMap<>();
        pa.put("exit", "true");
//        phantomJS("http://localhost:" + PORT , pa);
    }

    private static String getResourcePath(String path) {
        path = ReportRuntime.class.getClassLoader().getResource(path).getPath();
        return System.getProperty("os.name").contains(WINDOWS) ? path.substring(1) : path;
    }
}
