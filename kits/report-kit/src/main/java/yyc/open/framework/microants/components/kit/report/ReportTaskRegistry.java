package yyc.open.framework.microants.components.kit.report;

import com.google.common.collect.Maps;
import yyc.open.framework.microants.components.kit.report.commons.Processor;
import yyc.open.framework.microants.components.kit.report.listener.Listener;

import java.util.Map;

import static yyc.open.framework.microants.components.kit.report.commons.ReportConstants.LISTENER;

/**
 * {@link ReportTaskRegistry}
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/7/30
 */
@Processor(name = "registerListener", type = LISTENER)
public class ReportTaskRegistry implements Listener {
    private Map<String, ReportTask> tasks = Maps.newHashMap();

    enum ReportRegistryEnum {
        INSTANCE;

        ReportTaskRegistry reportRegistry;

        ReportTaskRegistry getReportRegistry() {
            if (reportRegistry == null) {
                reportRegistry = new ReportTaskRegistry();
            }
            return reportRegistry;
        }
    }

    @Override
    public void onSuccess(ReportEvent event) {

    }

    @Override
    public void onFailure(ReportEvent event) {

    }
}
