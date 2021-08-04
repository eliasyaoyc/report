package yyc.open.framework.microants.components.kit.report.entity;

import lombok.Getter;
import lombok.Setter;
import yyc.open.framework.microants.components.kit.common.validate.Nullable;
import yyc.open.framework.microants.components.kit.report.commons.ReportEnums;

import java.util.List;
import java.util.Map;

/**
 * {@link ReportData}
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/8/2
 */
@Setter
@Getter
public class ReportData {
    /**
     * Internally generated.
     */
    private String taskId;

    /**
     * The absolute template path for generation.
     */
    private @Nullable
    String templateUrl;

    /**
     * Chart title.
     */
    private String title;

    private ReportEnums type;

    /**
     * The legend name that only to Bar and Pie.
     */
    private String legendName;

    private List<Object[]> xdatas;

    private Object[] yDatas;

    private List<String> texts;

    private List<List<String>> tables;

    private Map<String, Object> statistics;

    private String statDescription;

    private String base64;

    /**
     * Create text data.
     *
     * @param texts
     * @return
     */
    public static ReportData text(List<String> texts) {
        ReportData reportData = new ReportData();
        reportData.setTexts(texts);
        return reportData;
    }

    /**
     * Create table data.
     *
     * @param tables
     * @return
     */
    public static ReportData table(List<List<String>> tables) {
        ReportData reportData = new ReportData();
        reportData.setTables(tables);
        return reportData;
    }

    /**
     * Create statistics data.
     *
     * @param statistics
     * @return
     */
    public static ReportData statistics(Map<String, Object> statistics) {
        ReportData reportData = new ReportData();
        reportData.setStatistics(statistics);
        return reportData;
    }

    /**
     * Create statistics data.
     *
     * @param statistics
     * @return
     */
    public static ReportData statistics(Map<String, Object> statistics,String description) {
        ReportData reportData = new ReportData();
        reportData.setStatistics(statistics);
        reportData.setStatDescription(description);
        return reportData;
    }


    public static ReportData image(String image) {
        ReportData reportData = new ReportData();
        reportData.setBase64(image);
        return reportData;
    }

    /**
     * Create echarts data.
     *
     * @param title
     * @param type
     * @param xdatas
     * @param yDatas
     * @return
     */
    public static ReportData echart(String title,
                                    ReportEnums type,
                                    List<Object[]> xdatas,
                                    Object[] yDatas) {
        return echart(title, "", type, "", xdatas, yDatas);
    }

    public static ReportData echart(String title,
                                    ReportEnums type,
                                    String legendName,
                                    List<Object[]> xdatas,
                                    Object[] yDatas) {
        return echart(title, "", type, legendName, xdatas, yDatas);
    }

    public static ReportData echart(String title,
                                    String templateUrl,
                                    ReportEnums type,
                                    String legendName,
                                    List<Object[]> xdatas,
                                    Object[] yDatas) {
        ReportData reportData = new ReportData();
        reportData.setTitle(title);
        reportData.setType(type);
        reportData.setTemplateUrl(templateUrl);
        reportData.setLegendName(legendName);
        reportData.setXdatas(xdatas);
        reportData.setYDatas(yDatas);
        return reportData;
    }
}
