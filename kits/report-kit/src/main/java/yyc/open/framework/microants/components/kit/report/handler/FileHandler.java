package yyc.open.framework.microants.components.kit.report.handler;


import com.deepoove.poi.data.PictureType;
import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import yyc.open.framework.microants.components.kit.common.annotations.VisibleForTesting;
import yyc.open.framework.microants.components.kit.common.validate.Asserts;
import yyc.open.framework.microants.components.kit.report.*;
import yyc.open.framework.microants.components.kit.report.commons.Processor;
import yyc.open.framework.microants.components.kit.report.commons.ReportEnums;
import yyc.open.framework.microants.components.kit.report.entity.ReportData;
import yyc.open.framework.microants.components.kit.report.exceptions.ReportException;
import yyc.open.framework.microants.components.kit.thirdpart.xwpdt.FastWordKit;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

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
    private ReportConfig reportConfig;

    @Override
    public void onHandle(T task, ReportConfig config, ReportCallback callback) {
        this.reportConfig = config;
        if (task instanceof ReportTask) {
            return;
        }

        ReportMetadata metadata = (ReportMetadata) task;
        LOGGER.info("[FileHandler] handle the report: {}, metadata: {} .", metadata.getReportId(), metadata);

        BufferedWriter writer = null;
        try {
            String fileName = null;

            // Determine which file convert to.
            switch (metadata.getReportType()) {
                case WORD:
                    fileName = metadata.getPath() + metadata.getReportId() + ".docx";

                    FastWordKit.FastWordBuilder builder = FastWordKit.builder()
                            .outputPath(metadata.getPath())
                            .fileName(metadata.getReportId() + ".docx");
                    if (!Objects.isNull(metadata.getTitle())) {
                        builder.image(metadata.getTitle().getBackground(), PictureType.suggestFileType(metadata.getTitle().getBackground()));
                        builder.smallText(metadata.getTitle().getDescription(), true);
                        builder.text(metadata.getTitle().getTitle(), 48d, true);
                    }

                    if (!Objects.isNull(metadata.getInfo())) {
                        Map<String, String> labels = metadata.getInfo().getLabels();
                        List<String> keys = new ArrayList<>(labels.keySet());
                        List<String> values = new ArrayList<>(labels.values());
                        builder.table(Arrays.asList(keys, values), true);
                        builder.blank();
                        builder.image(metadata.getInfo().getImage(), PictureType.suggestFileType(metadata.getInfo().getImage()));
                    }

                    if (!Objects.isNull(metadata.getCatalogue())) {
                        Map<String, List<String>> chapters = metadata.getCatalogue().getChapters();
                        chapters.entrySet().stream().forEach(chapter -> {
                            builder.bigText(chapter.getKey(), true);
                            chapter.getValue().stream().forEach(index -> {
                                builder.middleText(index, true);
                            });
                        });
                    }

                    if (!Objects.isNull(metadata.getContent())) {
                        ReportMetadata.ReportContent content = metadata.getContent();

                        for (int i = 0; i < content.getChapter().size(); i++) {
                            builder.blank();
                            builder.bigText(content.getChapter().get(i));

                            List<String> indices = content.getIndices().get(i);
                            List<String> description = content.getDescription().get(i);
                            List<ReportData> reportData = content.getData().get(i);

                            for (int j = 0; j < indices.size(); j++) {
                                builder.blank();
                                builder.middleText(indices.get(j));
                                if (description.size() > j) {
                                    builder.text(description.get(j), "696969", "SimSun", 12d, false);
                                }

                                ReportData data = reportData.get(j);
                                if (CollectionUtils.isNotEmpty(data.getTables())) {
                                    List<List<String>> tables = data.getTables();
                                    builder.table(tables, false);

                                } else if (CollectionUtils.isNotEmpty(data.getTexts())) {
                                    data.getTexts().stream().forEach(text -> {
                                        builder.text(text);
                                    });

                                } else if (StringUtils.isNotEmpty(data.getBase64())) {
                                    builder.image(data.getBase64(), PictureType.PNG);

                                } else if (!data.getStatistics().isEmpty()) {
                                    Map<String, Object> statistics = data.getStatistics();
                                    List<String> keys = new ArrayList<>(statistics.keySet());
                                    List<String> values = statistics.values().stream().map(val -> val.toString()).collect(Collectors.toList());
                                    builder.table(Arrays.asList(keys, values), false);
                                }
                            }
                        }
                    }
                    builder.build().create();
                    break;

                default:
                    // Generate html.
                    String option = StringUtils.isNotEmpty(metadata.getTemplatePath()) ?
                            generateFreemarkerTemplate(metadata.getTemplatePath(), assembleReportEntity(metadata)) :
                            generateFreemarkerTemplateByDefault(config.getTemplatesPath(), metadata.getReportType().getTemplateName(), assembleReportEntity(metadata));

                    fileName = metadata.getPath() + metadata.getReportId() + ".html";
                    File file = new File(fileName);

                    if (!file.getParentFile().exists()) {
                        file.getParentFile().mkdirs();
                    }

                    LOGGER.info("[FileHandler] write html json: {}", option);

                    writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8));
                    writer.write(option);
                    writer.flush();

                    // Determine whether convert to pdf.
                    if (metadata.getReportType() == ReportEnums.PDF) {
                        fileName = convertToPdf(fileName, this.reportConfig);
                    }
            }

            callback.onReceived(metadata.getReportId(), fileName, ReportEvent.EventType.COMPLETED);
        } catch (Exception e) {
            LOGGER.error("[FileHandler] generate report encountered error: {}.", e);
            callback.onException(metadata.getReportId(), String.format("[FileHandler] generate report encountered error: %s.", e));
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String convertToPdf(String htmlPath, ReportConfig config) {
        Asserts.isTrue(htmlPath.contains(".html"), "html file.");

        File file = new File(htmlPath);
        if (!file.exists()) {
            throw new ReportException("HTML is empty.");
        }

        String newName = file.getName().replace(".html", ".pdf");
        String filePath = file.getParent();
        ReportPlatforms.pdfConvertCommand(htmlPath, newName, filePath, config.getExecPath(), config.getJsPdfPath());
        return htmlPath.replace(".html", ".pdf");
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

        if (!info.getImage().isEmpty()) {
            report.put("infoImage", info.getImage());
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

                } else if (data.getStatistics() != null) {
                    contents.add(Content.statistics(indices.get(j),
                            description.size() > j ? description.get(j) : null,
                            data.getStatistics(), data.getStatDescription()));

                } else {
                    contents.add(new Content());
                }
            }
            cont.put(content.getChapter().get(i), contents);
        }
        report.put("content", cont);
        return report;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Content {
        private String index;
        private String description;
        private String base64;
        private List<String> texts;
        private List<String> tablesTitle;
        private List<List<String>> tablesValue;
        private Map<String, Object> stat;
        private String statDescription;

        public static Content image(String index, String description, String image) {
            return new Content(index, description, image, null, null, null, null, null);
        }

        public static Content text(String index, String description, List<String> text) {
            return new Content(index, description, null, text, null, null, null, null);
        }

        public static Content tables(String index, String description, List<List<String>> tables) {
            return new Content(index, description, null, null, tables.remove(0), tables, null, null);
        }

        public static Content statistics(String index, String description, Map<String, Object> statistics, String statDescription) {
            return new Content(index, description, null, null, null, null, statistics, statDescription);
        }
    }

    @VisibleForTesting
    public void generateHTML(ReportMetadata metadata) {
        try {
            String option = generateFreemarkerTemplateByDefault("templates/", metadata.getReportType().getTemplateName(), assembleReportEntity(metadata));
            System.out.println(option);
            String html = "/Users/eliasyao/Desktop/" + metadata.getReportId() + ".html";
            String pdf = "/Users/eliasyao/Desktop/" + metadata.getReportId() + ".pdf";
            File file = new File(html);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
            writer.write(option);
            writer.flush();
            System.out.println(option);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @VisibleForTesting
    public void generateWord(ReportMetadata metadata) {
        try {
            FastWordKit.FastWordBuilder builder = FastWordKit.builder()
                    .outputPath("/Users/eliasyao/Desktop/")
                    .fileName(metadata.getReportName() + ".docx");
            if (!Objects.isNull(metadata.getTitle())) {
                builder.image(metadata.getTitle().getBackground(), PictureType.PNG);
                builder.smallText(metadata.getTitle().getDescription(), true);
                builder.text(metadata.getTitle().getTitle(), 48d, true);
            }

            if (!Objects.isNull(metadata.getInfo())) {
                Map<String, String> labels = metadata.getInfo().getLabels();
                List<String> keys = new ArrayList<>(labels.keySet());
                List<String> values = new ArrayList<>(labels.values());
                builder.table(Arrays.asList(keys, values), true);
            }

            if (!Objects.isNull(metadata.getCatalogue())) {
                builder.blank();
                builder.image("/Users/eliasyao/Desktop/img_directory.png", PictureType.PNG);
                Map<String, List<String>> chapters = metadata.getCatalogue().getChapters();
                chapters.entrySet().stream().forEach(chapter -> {
                    builder.bigText(chapter.getKey(), true);
                    chapter.getValue().stream().forEach(index -> {
                        builder.middleText(index, true);
                    });
                });
            }

            if (!Objects.isNull(metadata.getContent())) {
                ReportMetadata.ReportContent content = metadata.getContent();

                for (int i = 0; i < content.getChapter().size(); i++) {
                    builder.blank();
                    builder.bigText(content.getChapter().get(i));

                    List<String> indices = content.getIndices().get(i);
                    List<String> description = content.getDescription().get(i);
                    List<ReportData> reportData = content.getData().get(i);

                    for (int j = 0; j < indices.size(); j++) {
                        builder.blank();
                        builder.middleText(indices.get(j));
                        if (description.size() > j) {
                            builder.text(description.get(j), "696969", "SimSun", 12d, false);
                        }

                        ReportData data = reportData.get(j);
                        if (CollectionUtils.isNotEmpty(data.getTables())) {
                            List<List<String>> tables = data.getTables();
                            builder.table(tables, false);

                        } else if (CollectionUtils.isNotEmpty(data.getTexts())) {
                            data.getTexts().stream().forEach(text -> {
                                builder.text(text);
                            });

                        } else if (StringUtils.isNotEmpty(data.getBase64())) {
                            builder.image(data.getBase64(), PictureType.PNG);

                        } else if (data.getStatistics() != null) {
                            Map<String, Object> statistics = data.getStatistics();
                            List<String> keys = new ArrayList<>(statistics.keySet());
                            List<String> values = statistics.values().stream().map(val -> val.toString()).collect(Collectors.toList());
                            builder.table(Arrays.asList(keys, values), false);
                        }
                    }
                }
            }
            builder.build().create();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
