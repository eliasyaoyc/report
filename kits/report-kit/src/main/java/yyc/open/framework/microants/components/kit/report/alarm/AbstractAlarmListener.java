package yyc.open.framework.microants.components.kit.report.alarm;

import yyc.open.framework.microants.components.kit.report.ReportEvent;
import yyc.open.framework.microants.components.kit.report.commons.Processor;
import yyc.open.framework.microants.components.kit.report.listener.Listener;

import static yyc.open.framework.microants.components.kit.report.commons.ReportConstants.LISTENER;

/**
 * {@link AbstractAlarmListener}
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/7/30
 */
@Processor(name = "alarmListener", type = LISTENER)
public abstract class AbstractAlarmListener implements Listener, alarm {
    @Override
    public void onAlarm(ReportEvent event) {

    }

    @Override
    public void onSuccess(ReportEvent event) {

    }

    @Override
    public void onFailure(ReportEvent event) {

    }
}
