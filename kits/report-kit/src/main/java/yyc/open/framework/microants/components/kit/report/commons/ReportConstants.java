package yyc.open.framework.microants.components.kit.report.commons;

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
        /**
         * Hollow pie chart.
         */
        String HOLLOW_PIE = "hollow-pie";
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
}
