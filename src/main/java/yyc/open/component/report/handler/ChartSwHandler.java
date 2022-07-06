package yyc.open.component.report.handler;

import static yyc.open.component.report.commons.ReportConstants.CHART_SW_HANDLE;
import static yyc.open.component.report.commons.ReportConstants.HANDLER;

import yyc.open.component.report.ReportCallback;
import yyc.open.component.report.ReportConfig;
import yyc.open.component.report.commons.Processor;

/**
 * @author Elias (siran0611@gmail.com)
 */
@Processor(name = CHART_SW_HANDLE, type = HANDLER)
public class ChartSwHandler<T> extends AbstractHandler<T> {

	@Override
	public void onHandle(T task, ReportConfig config, ReportCallback callback) {

	}
}
