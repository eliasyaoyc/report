package yyc.open.framework.microants.components.kit.report.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import yyc.open.framework.microants.components.kit.report.commons.Processor;

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

    @Override
    public void onHandle(T task) {
    }
}
