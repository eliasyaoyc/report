package yyc.open.framework.microants.components.kit.report.entity;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

/**
 * {@link ReportEntity}
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/8/1
 */
@Getter
@Builder
public class ReportEntity {
//    /**
//     * Chart title.
//     */
//    private String title;
//
//    /**
//     * The legend name that only to Bar and Pie.
//     */
//    private String legendName;
//
//
//    private List<Object[]> xdatas;
//
//    private Object[] yDatas;

    /**
     * Report title.
     */
    private ReportTitle title;

    /**
     * Report info.
     */
    private ReportInfo info;

    /**
     * Report catalogue.
     */
    private ReportCatalogue catalogue;

    /**
     * Report content.
     */
    private ReportContent content;

    /**
     * Inner class about report title part.
     */
    @Getter
    @Builder
    public class ReportTitle {
        private String background;
        private String title;
        private String description;
    }

    /**
     * Inner class about report info part (e.g. generate-time, report range time etc.)
     */
    @Getter
    @Builder
    public class ReportInfo {
        private String label;
        private String value;
    }

    /**
     * Inner class about report catalogue.
     */
    @Getter
    @Builder
    public class ReportCatalogue {
        private List<String> chapter;
        private List<List<String>> indices;
    }

    /**
     * Inner class about report content.
     */
    @Getter
    @Builder
    public class ReportContent {
        private String chapter;
        private List<String> indices;
        private List<String> description;
        private List<Object> data;
    }
}
