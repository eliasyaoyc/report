package yyc.open.component.report.listener;


import yyc.open.component.report.ReportEvent;

/**
 * {@link Listener}
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/7/28
 */
public interface Listener {
    void onSuccess(ReportEvent event);

    void onFailure(ReportEvent event);
}
