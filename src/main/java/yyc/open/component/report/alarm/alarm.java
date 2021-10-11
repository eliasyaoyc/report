package yyc.open.component.report.alarm;

import yyc.open.component.report.ReportEvent;

/**
 * {@link alarm}
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/7/28
 */
public interface alarm {

	void onAlarm(ReportEvent event);
}
