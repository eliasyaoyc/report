package yyc.open.framework.microants.components.kit.report.handler;

import yyc.open.framework.microants.components.kit.report.event.Event;

/**
 * {@link Handler}
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/7/28
 */
public interface Handler {
    void onHandle(Event event);

    default void onCompletion() {

    }
}
