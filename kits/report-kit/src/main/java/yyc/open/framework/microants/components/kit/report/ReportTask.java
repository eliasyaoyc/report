package yyc.open.framework.microants.components.kit.report;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import yyc.open.framework.microants.components.kit.report.entity.ReportData;

/**
 * {@link ReportTask}
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/7/30
 */
@Builder
@Getter
@Setter
public class ReportTask extends Task {
    private String reportId;
    private String reportName;
    private String outputPath;
    private String taskId;
    private Integer priority;
    private String templatePath;
    private String checksum;
    private String data;

    public static String parseReportData(ReportData data) {
        Objects.requireNonNull(data, "ReportData");
        Gson gson = new GsonBuilder().create();
        Map<String, Object> rest = Maps.newHashMap();
        switch (data.getType()) {
            case BAR:
                rest.put("title", data.getTitle());
                rest.put("legend", data.getLegendName());
                rest.put("category", gson.toJson(data.getXdatas().get(0)));
                rest.put("values", gson.toJson(data.getYDatas()));
                break;
            case CROSS_BAR:
                rest.put("title", data.getTitle());
                rest.put("legend", data.getLegendName());
                rest.put("category", gson.toJson(data.getXdatas().get(0)));
                rest.put("values", gson.toJson(data.getYDatas()));
                break;
            case CROSS_MULTI_BAR:
                // TODO
                break;
            case LINE:
                rest.put("title", data.getTitle());
                rest.put("legend", data.getLegendName());
                rest.put("category", gson.toJson(data.getYDatas()));
                rest.put("values", gson.toJson(data.getXdatas().get(0)));
                rest.put("interval", gson.toJson(data.getXdatas().get(0).length/20));
                break;
            case PIE:
                List<String> types = new ArrayList<>();
                List<Map<String, Object>> datas = new ArrayList<>();

                assemblePie(types, datas, data.getXdatas().get(0), data.getYDatas());
                rest.put("title", data.getTitle());
                rest.put("types", gson.toJson(types));
                rest.put("datas", gson.toJson(datas));
                break;
            case HOLLOW_PIE:
                break;
        }
        return gson.toJson(rest);
    }

    private static void assemblePie(List<String> stringList,
                                    List<Map<String, Object>> listMap,
                                    Object[] types,
                                    Object[] datas) {
        // Calculate sum.
        Long sum = Arrays.stream(datas).mapToLong(item -> Long.valueOf(item.toString())).sum();

        DecimalFormat df = new DecimalFormat(".00");

        for (int i = 0; i < types.length; i++) {
            float v = new BigDecimal(Float.valueOf(datas[i].toString()) / Float.valueOf(sum) * 100)
                .setScale(1, BigDecimal.ROUND_HALF_UP).floatValue();

            String typeParam = types[i] + ":" + datas[i] + "(" + v + "%)";

            stringList.add(typeParam);

            Map<String, Object> mapStr = new HashMap<>();
            mapStr.put("name", typeParam);
            mapStr.put("value", datas[i]);

            listMap.add(mapStr);
        }
    }
}
