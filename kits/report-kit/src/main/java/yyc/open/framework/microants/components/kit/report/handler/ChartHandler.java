package yyc.open.framework.microants.components.kit.report.handler;

import yyc.open.framework.microants.components.kit.report.ReportEvent;
import yyc.open.framework.microants.components.kit.report.commons.Processor;

import static yyc.open.framework.microants.components.kit.report.commons.ReportConstants.HANDLER;

/**
 * {@link ChartHandler}
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/7/28
 */
@Processor(name = "chartHandler", type = HANDLER)
public class ChartHandler implements Handler {
    @Override
    public void onHandle(ReportEvent event) {

    }
}
