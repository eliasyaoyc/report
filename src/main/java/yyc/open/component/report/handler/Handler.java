package yyc.open.component.report.handler;


import yyc.open.component.report.ReportCallback;
import yyc.open.component.report.ReportConfig;

/**
 * {@link Handler}
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/7/28
 */
public interface Handler<T> {
    void onHandle(T task, ReportConfig config, ReportCallback callback);
}
