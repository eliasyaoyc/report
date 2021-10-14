package yyc.open.component.report.handler;


import static yyc.open.component.report.commons.ReportConstants.FILE_HANDLE;
import static yyc.open.component.report.commons.ReportConstants.HANDLER;

import com.google.common.collect.Maps;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import yyc.open.component.report.ReportCallback;
import yyc.open.component.report.ReportConfig;
import yyc.open.component.report.ReportEvent;
import yyc.open.component.report.ReportMetadata;
import yyc.open.component.report.ReportPlatforms;
import yyc.open.component.report.ReportTask;
import yyc.open.component.report.commons.FastWordKitV2;
import yyc.open.component.report.commons.Processor;
import yyc.open.component.report.commons.ReportEnums;
import yyc.open.component.report.commons.validate.Asserts;
import yyc.open.component.report.entity.ReportData;
import yyc.open.component.report.exceptions.ReportException;

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

					FastWordKitV2 builder = new FastWordKitV2(metadata, true);
//					if (!Objects.isNull(metadata.getTitle())) {
//						builder.image(metadata.getTitle().getBackground(), 500, 450);
//						builder.text(metadata.getTitle().getDescription(), "696969", 12, true);
//						builder.title(metadata.getTitle().getTitle());
//					}
//
//					if (!Objects.isNull(metadata.getInfo())) {
//						Map<String, String> labels = metadata.getInfo().getLabels();
//						List<String> keys = new ArrayList<>(labels.keySet());
//						List<String> values = new ArrayList<>(labels.values());
//						builder.blank();
//						builder.table(Arrays.asList(keys, values));
//					}

					if (!Objects.isNull(metadata.getCatalogue())) {
						builder.mulu();
					}

					if (!Objects.isNull(metadata.getContent())) {
						ReportMetadata.ReportContent content = metadata.getContent();

						for (int i = 0; i < content.getChapter().size(); i++) {
							builder.heading1(content.getChapter().get(i));

							List<String> indices = content.getIndices().get(i);
							List<String> description = content.getDescription().get(i);
							List<ReportData> reportData = content.getData().get(i);

							for (int j = 0; j < indices.size(); j++) {
								builder.blank();
								builder.heading2(indices.get(j));
								if (description.size() > j) {
									builder.text(description.get(j), "696969", 12);
								}

								ReportData data = reportData.get(j);
								if (CollectionUtils.isNotEmpty(data.getTables())) {
									List<List<String>> tables = data.getTables();
									builder.blank();
									builder.table(tables);

								} else if (CollectionUtils.isNotEmpty(data.getTexts())) {
									data.getTexts().stream().forEach(text -> {
										builder.blank();
										builder.text(text, "000000", 14);
									});

								} else if (StringUtils.isNotEmpty(data.getBase64())) {
									builder.blank();
									builder.image(data.getBase64(), 500, 200);

								} else if (!data.getStatistics().isEmpty()) {
									Map<String, Object> statistics = data.getStatistics();
									List<String> keys = new ArrayList<>(statistics.keySet());
									List<String> values = statistics.values().stream().map(val -> val.toString()).collect(Collectors.toList());
									builder.blank();
									builder.table(Arrays.asList(keys, values));
								}
							}
						}
					}
					builder.create();
					break;

				default:
					// Generate html.
					String option = StringUtils.isNotEmpty(metadata.getTemplatePath()) ?
							generateFreemarkerTemplate(metadata.getTemplatePath(), assembleReportEntity(metadata)) :
							generateFreemarkerTemplateByDefault(config.getTemplatesPath(), metadata.getReportType().getTemplateName(),
									assembleReportEntity(metadata));

					fileName = metadata.getPath() + metadata.getReportId() + ".html";
					File file = new File(fileName);

					if (!file.getParentFile().exists()) {
						file.getParentFile().mkdirs();
					}

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
			LOGGER.error("[FileHandler] generate report encountered error.", e);
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
}
