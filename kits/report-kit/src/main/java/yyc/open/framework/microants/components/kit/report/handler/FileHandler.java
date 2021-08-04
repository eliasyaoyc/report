package yyc.open.framework.microants.components.kit.report.handler;


import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import freemarker.template.TemplateException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import yyc.open.framework.microants.components.kit.common.annotations.VisibleForTesting;
import yyc.open.framework.microants.components.kit.report.ReportCallback;
import yyc.open.framework.microants.components.kit.report.ReportConfig;
import yyc.open.framework.microants.components.kit.report.ReportEvent;
import yyc.open.framework.microants.components.kit.report.ReportMetadata;
import yyc.open.framework.microants.components.kit.report.commons.Processor;
import yyc.open.framework.microants.components.kit.report.entity.ReportData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static yyc.open.framework.microants.components.kit.report.commons.ReportConstants.FILE_HANDLE;
import static yyc.open.framework.microants.components.kit.report.commons.ReportConstants.HANDLER;

/**
 * {@link FileHandler}
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/7/28
 */
@Processor(name = FILE_HANDLE, type = HANDLER)
public class FileHandler<T> extends AbstractHandler<T> {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileHandler.class);

    @Override
    public void onHandle(T task, ReportConfig config, ReportCallback callback) {
        ReportMetadata metadata = (ReportMetadata) task;
        LOGGER.info("[FileHandler] handle the report: {}, metadata: {} .", metadata.getReportId(), metadata);

        Gson gson = new GsonBuilder().create();

        try {
            // Generate html.
            String option = StringUtils.isNotEmpty(metadata.getTemplatePath()) ?
                    generateFreemarkerTemplate(metadata.getTemplatePath(), assembleReportEntity(metadata)) :
                    generateFreemarkerTemplateByDefault(metadata.getReportType().getTemplateName(), assembleReportEntity(metadata));

            // Determine which file convert to.


            callback.onReceived(metadata.getReportId(), "", ReportEvent.EventType.COMPLETED);
        } catch (Exception e) {
            String msg = String.format("[FileHandler] generate report encountered error: %s.", e);
            LOGGER.error(msg);
            callback.onException(metadata.getReportId(), msg);
        }
    }

    void convertToPdf() {

    }

    void convertToWord() {

    }

    /**
     * Encapsulate and verity the last generation report whether valid.
     * First, we should check whether has values what content type is charts.
     * Next, checks report corresponding attributes is not empty.
     *
     * @param entity Report metadata.
     */
    Map<String, Object> assembleReportEntity(ReportMetadata entity) {
        Map<String, Object> report = Maps.newHashMap();
        report.put("title", entity.getTitle().getTitle());
        report.put("titleDesc", entity.getTitle().getDescription());
        report.put("background", entity.getTitle().getBackground());

        ReportMetadata.ReportInfo info = entity.getInfo();
        if (!info.getLabels().isEmpty()) {
            report.put("info", info.getLabels());
        }

        ReportMetadata.ReportCatalogue catalogue = entity.getCatalogue();
        if (!catalogue.getChapters().isEmpty()) {
            report.put("catalogue", catalogue.getChapters());
        }

        // Verity content.
        ReportMetadata.ReportContent content = entity.getContent();
        Map<String, List<Content>> cont = Maps.newLinkedHashMap();
        for (int i = 0; i < content.getChapter().size(); i++) {

            List<String> indices = content.getIndices().get(i);
            List<String> description = content.getDescription().get(i);
            List<ReportData> reportData = content.getData().get(i);

            List<Content> contents = new ArrayList<>();
            for (int j = 0; j < indices.size(); j++) {
                ReportData data = reportData.get(j);
                if (CollectionUtils.isNotEmpty(data.getTables())) {
                    contents.add(Content.tables(indices.get(j),
                            description.size() > j ? description.get(j) : null,
                            data.getTables()));

                } else if (CollectionUtils.isNotEmpty(data.getTexts())) {
                    contents.add(Content.text(indices.get(j),
                            description.size() > j ? description.get(j) : null,
                            data.getTexts()));

                } else if (StringUtils.isNotEmpty(data.getBase64())) {
                    contents.add(Content.image(indices.get(j),
                            description.size() > j ? description.get(j) : null,
                            data.getBase64()));

                } else if (!data.getStatistics().isEmpty()) {
                    contents.add(Content.statistics(indices.get(j),
                            description.size() > j ? description.get(j) : null,
                            data.getStatistics()));

                } else {
                    contents.add(new Content());
                }
            }
            cont.put(content.getChapter().get(i), contents);
        }
        report.put("content", cont);

//        Map<String, List<Content>> testMap = new HashMap<>();
//        List<Content> listOne = new ArrayList<>();
//        List<Content> listTwo = new ArrayList<>();
//        listOne.add(new Content("1.1 王大麻子"));
////        listOne.add(new Content("1.1 王大麻子",null,null,null,null,null,null));
//        listOne.add(new Content("1.2 李二狗"));
//        listTwo.add(new Content("1.3 郑老六"));
//        listTwo.add(new Content("1.4 吴大棒槌"));
//        testMap.put("classA", listOne);
//        testMap.put("classB", listTwo);
//        report.put("content", testMap);

        return report;
    }

//    @Getter
//    @Setter
//    public class User {
//        private String index;
//        private Integer age;
//
//        public User(String index, Integer age) {
//            this.index = index;
//            this.age = age;
//        }
//    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Content {
        private String index;
        private String description;
        private String base64;
        private List<String> text;
        private List<String> tablesTitle;
        private List<List<String>> tables;
        private Map<String, Object> statistics;

        public static Content image(String index, String description, String image) {
            return new Content(index, description, image, null, null, null, null);
        }

        public static Content text(String index, String description, List<String> text) {
            return new Content(index, description, null, text, null, null, null);
        }

        public static Content tables(String index, String description, List<List<String>> tables) {
            return new Content(index, description, null, null, tables.remove(0), tables, null);
        }

        public static Content statistics(String index, String description, Map<String, Object> statistics) {
            return new Content(index, description, null, null, null, null, statistics);
        }
    }

    @VisibleForTesting
    public void generatePDF(ReportMetadata metadata) {
        Gson gson = new GsonBuilder().create();
        Map<String, Object> stringObjectMap = assembleReportEntity(metadata);
        String s = gson.toJson(stringObjectMap);
        System.out.println(s);
        try {
            String option = generateFreemarkerTemplateByDefault(metadata.getReportType().getTemplateName(), assembleReportEntity(metadata));
            System.out.println(option);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
    }
}
