package yyc.open.framework.microants.components.kit.report.handler;

import yyc.open.framework.microants.components.kit.report.ReportContext;
import yyc.open.framework.microants.components.kit.report.ReportEvent;

/**
 * {@link AbstractHandler}
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/7/31
 */
public abstract class AbstractHandler<T> implements Handler<T>{

    protected void reportCompleteness(String taskId){
        ReportEvent event = ReportEvent.builder()
                .taskId(taskId)
                .type(ReportEvent.EventType.PARTIALLY_COMPLETED)
                .build();
        ReportContext.listenerFactory().onEvent(event);
    }
}
