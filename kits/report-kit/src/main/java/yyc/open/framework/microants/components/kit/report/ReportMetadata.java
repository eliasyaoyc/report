package yyc.open.framework.microants.components.kit.report;

import com.google.common.collect.Maps;
import lombok.Getter;
import lombok.Setter;
import yyc.open.framework.microants.components.kit.common.uuid.UUIDsKit;
import yyc.open.framework.microants.components.kit.report.commons.ReportEnums;
import yyc.open.framework.microants.components.kit.report.entity.ReportData;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * {@link ReportMetadata}
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/8/1
 */
@Getter
@Setter
public class ReportMetadata extends Task {
    private String reportId = UUIDsKit.base64UUID();

    private String reportName = UUIDsKit.base64UUID();

    /**
     * Report title.
     */
    private ReportTitle title;

    private String templatePath;

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

    public ReportMetadata(String reportId,
                          String reportName,
                          ReportEnums reportType,
                          ReportTitle title,
                          ReportInfo info,
                          ReportCatalogue catalogue,
                          ReportContent content) {
        this.reportId = reportId;
        this.reportName = reportName;
        super.setReportType(reportType);
        this.title = title;
        this.info = info;
        this.catalogue = catalogue;
        this.content = content;
    }

    /**
     * Inner class about report title part.
     */
    @Getter
    @Setter
    public static class ReportTitle {
        private String background;
        private String title;
        private String description;
    }

    /**
     * Inner class about report info part (e.g. generate-time, report range time etc.)
     */
    @Getter
    @Setter
    public static class ReportInfo {
        private Map<String, String> labels = Maps.newLinkedHashMap();
        private String image;
    }

    /**
     * Inner class about report catalogue.
     */
    @Getter
    @Setter
    public static class ReportCatalogue {
        private Map<String, List<String>> chapters = Maps.newLinkedHashMap();
    }

    /**
     * Inner class about report content.
     */
    @Getter
    @Setter
    public static class ReportContent {
        private List<String> chapter = new ArrayList<>();
        private List<List<String>> indices = new ArrayList<>();
        private List<List<String>> description = new ArrayList<>();
        private List<List<ReportData>> data = new ArrayList<>();
    }

    public void setSubTaskExecutionResult(String taskId, String base64) {
        List<List<ReportData>> data = this.getContent().getData();
        data.stream().forEach(d -> {
            d.stream().filter(i -> ReportEnums.isCharts(i.getType())).forEach(echart -> {
                if (echart.getTaskId().equals(taskId)) {
                    echart.setBase64(base64);
                }
            });
        });
    }

    /**
     * Construct `reportEntity` used builder.
     */
    public static ReportMedataBuilder builder() {
        return new ReportMedataBuilder();
    }
}