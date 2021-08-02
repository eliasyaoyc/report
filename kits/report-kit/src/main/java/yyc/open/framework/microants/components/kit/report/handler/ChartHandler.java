package yyc.open.framework.microants.components.kit.report.handler;

import com.google.gson.GsonBuilder;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import yyc.open.framework.microants.components.kit.http.HttpKit;
import yyc.open.framework.microants.components.kit.http.Result;
import yyc.open.framework.microants.components.kit.report.ReportConfig;
import yyc.open.framework.microants.components.kit.report.ReportContext;
import yyc.open.framework.microants.components.kit.report.ReportContextFactory;
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

    {
        globalConfig = ReportContextFactory.ReportContextFactoryEnum.INSTANCE
                .getReportContextFactory()
                .getContext()
                .getGlobalConfig();
    }

    @Override
    public void onHandle(T task) {
        ReportTask.ReportTaskChild t = (ReportTask.ReportTaskChild) task;
        LOGGER.info("[Chard Handler] handle the task {}", t.getTaskId());

        try {
            String option = StringUtils.isNotEmpty(t.getTemplatePath()) ?
                    generateFreemarkerTemplate(t.getTemplatePath(), new HashMap<>()) :
                    generateFreemarkerTemplateByDefault(t.getType().getTemplateName(), new HashMap<>());

            Map<String, Object> opt = new GsonBuilder().create().fromJson(option, Map.class);

            PhantomJS req = PhantomJS.builder()
                    .opt(opt)
                    .reqMethod("echarts")
                    .file(t.getPath())
                    .build();

            Result result = HttpKit.builder()
                    .post(String.format("localhost:{}", globalConfig.getPort()))
                    .body(new GsonBuilder().create().toJson(req))
                    .build().get();

            // Determine whether to generate a watermark.
            if (StringUtils.isNotEmpty(globalConfig.getWatermark())) {
                generateWatermark(globalConfig.getWatermark(), t.getPath());
            }

            reportCompleteness(t.getTaskId());
        } catch (Exception e) {
            LOGGER.error("[Chart Handler] generate chart encountered error: {}", e);
            // add to fail queue.
            ReportContext.reportRegistry().addToFailQueue(t.getTaskId());
        }
    }
}
