package yyc.open.framework.microants.components.kit.report;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import yyc.open.framework.microants.components.kit.common.file.FileKit;
import yyc.open.framework.microants.components.kit.http.HttpKit;
import yyc.open.framework.microants.components.kit.report.exceptions.ReportException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * {@link ReportRuntime} The report generation runtime that providers phantomjs running and
 * record report status in generating.
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/7/28
 */
public class ReportRuntime implements AutoCloseable {
    private static final Logger logger = LoggerFactory.getLogger(ReportRuntime.class);

    private String GLOBAL_JSON_PATH = "server.json";
    private AtomicBoolean running;
    private ReportConfig.GlobalConfig globalConfig;

    {
        this.running = new AtomicBoolean(false);
    }

    private void initialize() {
        try {
            String ret = FileKit.readFileAsStream(GLOBAL_JSON_PATH);

            Gson gson = new GsonBuilder().create();
            globalConfig = gson.fromJson(ret, ReportConfig.GlobalConfig.class);

            Runtime.getRuntime().addShutdownHook(new Thread() {
                @Override
                public void run() {
                    close();
                }
            });
        } catch (IOException e) {
            throw new ReportException("[Report Runtime] initialize fail", e);
        }
    }

    /**
     * Start the phantomjs plugin.
     */
    public void start(boolean echarts) {
        if (this.running.get()) {
            logger.error("[Report Runtime] already running, please shutdown first.");
            return;
        }

        this.initialize();
        if (Objects.isNull(this.globalConfig)) {
            logger.error("[Report Runtime] Global config initialize not success yet.");
            return;
        }

        if (echarts) {
            ReportPlatforms.runPhantomStartCommand(globalConfig.getPort());
        }

        ReportContextProvider.INSTANCE.setContext(this.constructContext());

        this.running.compareAndSet(false, true);
    }

    /**
     * Construct a context that supports the report generation status report functional.
     *
     * @return
     */
    private ReportContext constructContext() {
        return ReportContext.builder()
                .globalConfig(this.globalConfig)
                .reportStatus(new ReportStatus())
                .build();
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
        if (!this.running.get()) {
            return;
        }

        HashMap<String, Object> pa = new HashMap<>();
        pa.put("exit", "true");
        HttpKit.builder()
                .post(String.format("http://127.0.0.1:%s", globalConfig.getPort()))
                .body(pa)
                .build()
                .get();

        ReportContextProvider.INSTANCE.close();

        this.running.compareAndSet(true, false);
    }
}
