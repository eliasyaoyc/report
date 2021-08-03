package yyc.open.framework.microants.components.kit.report.alarm;

import yyc.open.framework.microants.components.kit.report.ReportConfig;
import yyc.open.framework.microants.components.kit.report.ReportContext;
import yyc.open.framework.microants.components.kit.report.ReportContextProvider;
import yyc.open.framework.microants.components.kit.report.ReportEvent;
import yyc.open.framework.microants.components.kit.report.commons.Processor;
import yyc.open.framework.microants.components.kit.report.listener.Listener;

import static yyc.open.framework.microants.components.kit.report.commons.ReportConstants.ALARM_LISTENER;
import static yyc.open.framework.microants.components.kit.report.commons.ReportConstants.LISTENER;

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
