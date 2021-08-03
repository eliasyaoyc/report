package yyc.open.framework.microants.components.kit.report.handler;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import yyc.open.framework.microants.components.kit.report.ReportCallback;
import yyc.open.framework.microants.components.kit.report.ReportEvent;
import yyc.open.framework.microants.components.kit.report.commons.Processor;
import yyc.open.framework.microants.components.kit.report.entity.ReportEntity;

import java.util.HashMap;

import static yyc.open.framework.microants.components.kit.report.commons.ReportConstants.FILE_HANDLE;
import static yyc.open.framework.microants.components.kit.report.commons.ReportConstants.HANDLER;

/**
 * {@link FileHandler}
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/7/28
 */
@Processor(name = FILE_HANDLE, type = HANDLER)
public class FileHandler<T> extends AbstractHandler<T> {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileHandler.class);

    @Override
    public void onHandle(T task, ReportCallback callback) {
        ReportEntity t = (ReportEntity) task;
        LOGGER.info("[FileHandler] handle the report {}.", t.getReportId());

        Gson gson = new GsonBuilder().create();

        try {
            // Generate html.
            String option = StringUtils.isNotEmpty(t.getTemplatePath()) ?
                    generateFreemarkerTemplate(t.getTemplatePath(), new HashMap<>()) :
                    generateFreemarkerTemplateByDefault(t.getReportType().getTemplateName(), new HashMap<>());

            // Determine which file convert to.
            callback.onReceived(t.getReportId(), "", ReportEvent.EventType.COMPLETED);
        } catch (Exception e) {
            String msg = String.format("[FileHandler] generate report encountered error: {}.", e);
            LOGGER.error(msg);
            callback.onException(t.getReportId(), msg);
        }
    }

    void convertToPdf() {

    }

    void convertToWord() {

    }
}
