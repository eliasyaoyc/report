package yyc.open.framework.microants.components.kit.report;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import yyc.open.framework.microants.components.kit.report.exceptions.ReportException;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

import static sun.tools.jconsole.Messages.WINDOWS;

/**
 * {@link ReportRuntime}
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/7/28
 */
public class ReportRuntime implements AutoCloseable{
    private static final Logger logger = LoggerFactory.getLogger(ReportRuntime.class);

    private String GLOBAL_JSON_PATH = "server.json";
    private AtomicBoolean running;
    private ReportConfig.GlobalConfig globalConfig;

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

    private void initialize() {
        try {
            String ret = FileUtils.readFileToString(new File(GLOBAL_JSON_PATH), StandardCharsets.UTF_8);
            Gson gson = new GsonBuilder().create();
            globalConfig = gson.fromJson(ret, ReportConfig.GlobalConfig.class);
        } catch (IOException e) {
            throw new ReportException("[Report Runtime] initialize fail", e);
        }
    }

    /**
     * Start the phantomjs plugin.
     */
    public void start() {
        if (this.running.get()) {
            logger.error("[Report Runtime] already running, please shutdown first.");
            return;
        }

        this.initialize();
        if (Objects.isNull(this.globalConfig)) {
            logger.error("[Report Runtime] Global config initialize not success yet.");
            return;
        }

        String property = System.getProperty("os.name");
        Platforms platforms = Platforms.getPlatforms(property);
        String command = new StringBuilder(globalConfig.getExecPath())
                .append(getResourcePath(platforms.getPath()))
                .append(" ")
                .append(globalConfig.getEChartJsPath())
                .append(" -s -p ")
                .append(globalConfig.getPort()).toString();
        try {
            Runtime.getRuntime().exec(command);
        } catch (IOException e) {
            throw new ReportException(String.format("[Report Runtime] exec command: %s fail", command), e);
        }

        ReportContextFactory.ReportContextFactoryEnum.INSTANCE
                .getReportContextFactory()
                .setContext(constructContext());

        this.running.compareAndSet(false, true);
    }

    private ReportContext constructContext() {
        return new ReportContext();
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
