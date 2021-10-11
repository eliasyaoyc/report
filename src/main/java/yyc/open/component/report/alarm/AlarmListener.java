package yyc.open.component.report.alarm;


import static yyc.open.component.report.commons.ReportConstants.ALARM_LISTENER;
import static yyc.open.component.report.commons.ReportConstants.LISTENER;

import yyc.open.component.report.ReportConfig;
import yyc.open.component.report.ReportContext;
import yyc.open.component.report.ReportContextProvider;
import yyc.open.component.report.ReportEvent;
import yyc.open.component.report.commons.Processor;
import yyc.open.component.report.listener.Listener;

/**
 * {@link AlarmListener}
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/7/30
 */
@Processor(name = ALARM_LISTENER, type = LISTENER)
public class AlarmListener implements Listener, alarm {

	@Override
	public void onAlarm(ReportEvent event) {

	}

	@Override
	public void onSuccess(ReportEvent event) {
	}

	@Override
	public void onFailure(ReportEvent event) {
		// Determine if an alarm is needed.
		if (!needToAlarm()) {
			return;
		}
		onAlarm(event);
	}


	/**
	 * Determine the alarm whether is enabled.
	 *
	 * @return true if alarm is enabled, otherwise false.
	 */
	boolean needToAlarm() {
		ReportContext context = ReportContextProvider.INSTANCE.getContext();
		ReportConfig.AlarmConfig alarm = context.getGlobalConfig().getAlarm();
		return alarm.isEnabled();
	}
}
