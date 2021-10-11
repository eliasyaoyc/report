package yyc.open.component.report.handler;


import static yyc.open.component.report.commons.ReportConstants.CHART_HANDLE;
import static yyc.open.component.report.commons.ReportConstants.HANDLER;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import yyc.open.component.report.ReportCallback;
import yyc.open.component.report.ReportConfig;
import yyc.open.component.report.ReportEvent;
import yyc.open.component.report.ReportTask;
import yyc.open.component.report.commons.Processor;
import yyc.open.component.report.commons.http.HttpKit;
import yyc.open.component.report.commons.http.Result;
import yyc.open.component.report.entity.PhantomJS;

/**
 * {@link ChartHandler}
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/7/28
 */
@Processor(name = CHART_HANDLE, type = HANDLER)
public class ChartHandler<T> extends AbstractHandler<T> {

	private static final Logger LOGGER = LoggerFactory.getLogger(ChartHandler.class);

	public ChartHandler() {
	}

	@Override
	public void onHandle(T task, ReportConfig config, ReportCallback callback) {
		ReportTask t = (ReportTask) task;
		LOGGER.info("[ChardHandler] handle the task {}.", t.getTaskId());

		Gson gson = new GsonBuilder().create();
		try {
			String option = StringUtils.isNotEmpty(t.getTemplatePath()) ?
					generateFreemarkerTemplate(t.getTemplatePath(), gson.fromJson(t.getData(), Map.class)) :
					generateFreemarkerTemplateByDefault(config.getTemplatesPath(), t.getReportType().getTemplateName(),
							gson.fromJson(t.getData(), Map.class));

			Map<String, Object> opt = gson.fromJson(option, Map.class);

			String imageName = t.getOutputPath() + t.getTaskId() + ".png";

			PhantomJS req = PhantomJS.builder()
					.opt(opt)
					.reqMethod("echarts")
					.file(imageName)
					.build();

			Result result = HttpKit.builder()
					.post(String.format("http://127.0.0.1:%s", config.getPort()))
					.body(gson.toJson(req))
					.build().get();

			if (result.getCode() != 200) {
				String msg = String.format("[ChartHandler] generate echarts failed.");
				LOGGER.error(msg);
				callback.onException(t.getTaskId(), msg);
			}

			// Determine whether to generate a watermark.
			if (StringUtils.isNotEmpty(config.getWatermark())) {
				generateWatermark(config.getWatermark(), t.getOutputPath());
			}

			callback.onReceived(t.getTaskId(), imageName, ReportEvent.EventType.PARTIALLY_COMPLETED);
		} catch (Exception e) {
			String msg = String.format("[ChartHandler] generate echart encountered error: %s.", e);
			LOGGER.error("[ChartHandler] generate echart encountered error: {}.", e);
			callback.onException(t.getTaskId(), msg);
		}
	}
}
