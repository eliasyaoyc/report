package yyc.open.framework.microants.components.kit.report.commons;

import yyc.open.framework.microants.components.kit.report.commons.ReportConstants.Charts;
import yyc.open.framework.microants.components.kit.report.commons.ReportConstants.File;

/**
 * {@link ReportEnums}
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/7/28
 */
interface ReportEnumsInterface {
    default String getTemplateName() {
        return "html.ftl";
    }
}

public enum ReportEnums implements ReportEnumsInterface {
    BAR(Charts.BAR) {
        @Override
        public String getTemplateName() {
            return "barOption.ftl";
        }
    },
    CROSS_BAR(Charts.CROSS_BAR) {
        @Override
        public String getTemplateName() {
            return "crossBarOption.ftl";
        }
    },
    CROSS_MULTI_BAR(Charts.CROSS_MULTI_BAR) {
        @Override
        public String getTemplateName() {
            return "crossMultiBarOption.ftl";
        }
    },
    LINE(Charts.LINE) {
        @Override
        public String getTemplateName() {
            return "lineOption.ftl";
        }
    },
    PIE(Charts.PIE) {
        @Override
        public String getTemplateName() {
            return "pieOption.ftl";
        }
    },
    HOLLOW_PIE(Charts.HOLLOW_PIE) {
        @Override
        public String getTemplateName() {
            return "hollowPieOption.ftl";
        }
    },

    TABLE(Charts.TABLE),
    PDF(File.PDF),
    EXCEL(File.EXCEL),
    WORD(File.WORD),
    HTML(File.HTML);

    private String name;

    ReportEnums(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    /**
     * Determine the type whether belongs to chart.
     *
     * @param type
     * @return
     */
    public static boolean isCharts(ReportEnums type) {
        if (type == null) {
            return false;
        }

        if (type == ReportEnums.BAR ||
                type == ReportEnums.CROSS_BAR ||
                type == ReportEnums.CROSS_MULTI_BAR ||
                type == ReportEnums.LINE ||
                type == ReportEnums.PIE ||
                type == ReportEnums.HOLLOW_PIE) {
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