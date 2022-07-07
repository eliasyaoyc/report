package yyc.open.component.report.handler;

import static yyc.open.component.report.commons.ReportConstants.CHART_SW_HANDLE;
import static yyc.open.component.report.commons.ReportConstants.HANDLER;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import yyc.open.component.report.ReportCallback;
import yyc.open.component.report.ReportConfig;
import yyc.open.component.report.ReportEvent;
import yyc.open.component.report.ReportTask;
import yyc.open.component.report.commons.Processor;
import yyc.open.component.report.commons.ReportConstants.File;
import yyc.open.component.report.commons.file.FileKit;

/**
 * @author Elias (siran0611@gmail.com)
 */
@Processor(name = CHART_SW_HANDLE, type = HANDLER)
public class ChartSwHandler<T> extends AbstractHandler<T> {

	private static final Logger LOGGER = LoggerFactory.getLogger(ChartHandler.class);
	private final String NO_DATA_PATH;
	private final String SW_PREFIX = "sw_";

	public ChartSwHandler() {
		NO_DATA_PATH = FileKit.tempFile("templates/no_data.png");
	}

	@Override
	public void onHandle(T task, ReportConfig config, ReportCallback callback) {
		ReportTask t = (ReportTask) task;
		LOGGER.info("[ChardHandler] handle the task {}.", t.getTaskId());

		Gson gson = new GsonBuilder().create();
		try {
			String imageName = NO_DATA_PATH;

			if (StringUtils.isNotEmpty(t.getData())) {
				String option = StringUtils.isNotEmpty(t.getTemplatePath()) ?
						generateFreemarkerTemplate(t.getTemplatePath(), gson.fromJson(t.getData(), Map.class)) :
						generateFreemarkerTemplateByDefault(config.getTemplatesPath(), SW_PREFIX + t.getReportType().getTemplateName(),
								gson.fromJson(t.getData(), Map.class));

				// Generate html
				java.io.File temp = java.io.File.createTempFile(t.getTaskId(), ".txt");
				temp.deleteOnExit();
				BufferedWriter out = new BufferedWriter(new FileWriter(temp));
				out.write(option);
				out.close();

				// Invoke sw.js that generate echart.
				imageName = t.getOutputPath() + t.getTaskId() + ".png";

				String command = new StringBuilder("node ")
						.append(FileKit.tempFile("js/sw.js"))
						.append(" ")
						.append(temp.getAbsolutePath())
						.append(imageName).toString();

				Runtime.getRuntime().exec(command);

				// Clear.
				temp.deleteOnExit();
			}
			callback.onReceived(t.getTaskId(), imageName, ReportEvent.EventType.PARTIALLY_COMPLETED);
		} catch (Exception e) {
			String msg = String.format("[ChartHandler] generate echart encountered error: %s.", e);
			LOGGER.error("[ChartHandler] generate echart encountered error.", e);
			callback.onException(t.getTaskId(), msg);
		}
	}
}
