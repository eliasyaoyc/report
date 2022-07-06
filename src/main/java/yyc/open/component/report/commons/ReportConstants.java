package yyc.open.component.report.commons;

/**
 * {@link ReportConstants} constants collection.
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/7/28
 */
public interface ReportConstants {
    /**
     * Constants about chart e.g bar, cross-bar etc.
     */
    interface Charts {
        /**
         * Bar chart.
         */
        String BAR = "bar";
        /**
         * Horizontal single bar chart.
         */
        String CROSS_BAR = "cross-bar";
        /**
         * Horizontal multi bar chart.
         */
        String CROSS_MULTI_BAR = "cross-multi-bar";
        /**
         * Line chart.
         */
        String LINE = "line";
        /**
         * Pie chart.
         */
        String PIE = "pie";

        String MULTIPIE = "multipie";

        /**
         * Hollow pie chart.
         */
        String HOLLOW_PIE = "hollow-pie";

        /**
         * Table form
         */
        String TABLE = "table";
    }

    /**
     * Constants about file e.g. pdf, excel etc.
     */
    interface File {
        String PDF = "pdf";
        String EXCEL = "excel";
        String WORD = "word";
        String HTML = "html";
    }

    String LISTENER = "listener";
    String HANDLER = "handler";
    String ROOT_NAME = "yyc.open.framework.microants.components.kit.report";


    String CHART_HANDLE = "chartHandler";
    String CHART_SW_HANDLE = "chartSwHandler";
    String FILE_HANDLE = "fileHandler";
    String ALARM_LISTENER = "alarmListener";
    String DEFAULT_LISTENER = "defaultListener";
    String REGISTER_LISTENER = "registerListener";
}
