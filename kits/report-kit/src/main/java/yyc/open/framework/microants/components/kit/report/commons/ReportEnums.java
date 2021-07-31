package yyc.open.framework.microants.components.kit.report.commons;

import yyc.open.framework.microants.components.kit.report.commons.ReportConstants.Charts;
import yyc.open.framework.microants.components.kit.report.commons.ReportConstants.File;

/**
 * {@link ReportEnums}
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/7/28
 */
public enum ReportEnums {
    BAR(Charts.BAR),
    CROSS_BAR(Charts.CROSS_BAR),
    CROSS_MULTI_BAR(Charts.CROSS_MULTI_BAR),
    LINE(Charts.LINE),
    PIE(Charts.PIE),
    HOLLOW_PIE(Charts.HOLLOW_PIE),
    TABLE(Charts.TABLE),

    PDF(File.PDF),
    EXCEL(File.EXCEL),
    WORD(File.WORD),
    HTML(File.HTML);

    private String name;

    ReportEnums(String name) {
        this.name = name;
    }

    /**
     * Determine the type whether belongs to chart.
     * @param type
     * @return
     */
    public static boolean isCharts(ReportEnums type) {
        if (type == ReportEnums.BAR ||
                type == ReportEnums.CROSS_BAR ||
                type == ReportEnums.CROSS_MULTI_BAR ||
                type == ReportEnums.LINE ||
                type == ReportEnums.PIE ||
                type == ReportEnums.HOLLOW_PIE ||
                type == ReportEnums.TABLE) {
            return true;
        }
        return false;
    }

    /**
     * Returns the concrete {@link ReportEnums} through input param.
     *
     * @param typeName
     * @return
     */
    public static ReportEnums getType(String typeName) {
        for (ReportEnums enums : ReportEnums.values()) {
            if (enums.name.equals(typeName)) {
                return enums;
            }
        }
        throw new IllegalArgumentException("Input parameter is not support yet, please check it.");
    }
}