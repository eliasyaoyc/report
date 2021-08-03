package yyc.open.framework.microants.components.kit.report.handler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import yyc.open.framework.microants.components.kit.http.HttpKit;
import yyc.open.framework.microants.components.kit.http.Result;
import yyc.open.framework.microants.components.kit.report.ReportCallback;
import yyc.open.framework.microants.components.kit.report.ReportConfig;
import yyc.open.framework.microants.components.kit.report.ReportEvent;
import yyc.open.framework.microants.components.kit.report.ReportTask;
import yyc.open.framework.microants.components.kit.report.commons.Processor;
import yyc.open.framework.microants.components.kit.report.entity.PhantomJS;

import java.util.HashMap;
import java.util.Map;

import static yyc.open.framework.microants.components.kit.report.commons.ReportConstants.CHART_HANDLE;
import static yyc.open.framework.microants.components.kit.report.commons.ReportConstants.HANDLER;

/**
 * {@link ChartHandler}
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/7/28
 */
@Processor(name = CHART_HANDLE, type = HANDLER)
public class ChartHandler<T> extends AbstractHandler<T> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ChartHandler.class);

    private ReportConfig.GlobalConfig globalConfig;

    public ChartHandler() {
    }

    @Override
    public void onHandle(T task, ReportCallback callback) {
        ReportTask t = (ReportTask) task;
        LOGGER.info("[ChardHandler] handle the task {}.", t.getTaskId());

        Gson gson = new GsonBuilder().create();
        try {
            String option = StringUtils.isNotEmpty(t.getTemplatePath()) ?
                    generateFreemarkerTemplate(t.getTemplatePath(), new HashMap<>()) :
                    generateFreemarkerTemplateByDefault(t.getReportType().getTemplateName(), new HashMap<>());

            Map<String, Object> opt = gson.fromJson(option, Map.class);

            PhantomJS req = PhantomJS.builder()
                    .opt(opt)
                    .reqMethod("echarts")
                    .file(t.getOutputPath())
                    .build();

            Result result = HttpKit.builder()
                    .post(String.format("localhost:{}", globalConfig.getPort()))
                    .body(new GsonBuilder().create().toJson(req))
                    .build().get();

            // Get the base64.
            String ret = gson.fromJson(result.getMsg(), Map.class).get("data").toString();

            // Determine whether to generate a watermark.
            if (StringUtils.isNotEmpty(globalConfig.getWatermark())) {
                generateWatermark(globalConfig.getWatermark(), t.getOutputPath());
            }

            if (StringUtils.isEmpty(ret)) {
                callback.onException(t.getTaskId(), "[ChartHandler] generate echart encounter error: base64 is empty.");
                return;
            }
            callback.onReceived(t.getTaskId(), ret, ReportEvent.EventType.PARTIALLY_COMPLETED);
        } catch (Exception e) {
            String msg = String.format("[ChartHandler] generate echart encountered error: {}.", e);
            LOGGER.error(msg);
            callback.onException(t.getTaskId(), msg);
        }
    }
}
