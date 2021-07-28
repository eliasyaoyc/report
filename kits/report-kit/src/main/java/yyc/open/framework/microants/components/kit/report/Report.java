package yyc.open.framework.microants.components.kit.report;

import java.util.List;

/**
 * {@link Report}
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/7/28
 */
public class Report {
    private final List<ReportConfig> configs;

    public Report(List<ReportConfig> configs) {
        this.configs = configs;
    }

    /**
     * Generate the report that supports batch generation.
     *
     * @param configs
     */
    public void generateReport(List<ReportConfig> configs) {
        generate(configs, false);
    }

    /**
     * TODO next version.
     *
     * Generate the report, in additional supports parallel generation.
     *
     * @param configs
     */
    private void generateReportParallel(List<ReportConfig> configs) {
        generate(configs, true);
    }


    private void generate(List<ReportConfig> configs, boolean parallel) {

    }
}
