package yyc.open.component.report;

import com.google.common.collect.Maps;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import yyc.open.component.report.commons.ReportEnums;
import yyc.open.component.report.commons.uuid.UUIDsKit;
import yyc.open.component.report.commons.validate.Asserts;
import yyc.open.component.report.commons.validate.NonNull;
import yyc.open.component.report.entity.ReportData;
import yyc.open.component.report.exceptions.ReportDynamicCatalogueException;

/**
 * {@link ReportMedataBuilder} The builder model for {@link ReportMetadata}.
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/8/2
 */
public class ReportMedataBuilder {

	private String reportId = UUIDsKit.base64UUID();
	private String reportName = UUIDsKit.base64UUID();

	private String templatePath;
	private ReportEnums reportType;
	ReportMetadata.ReportTitle title;
	ReportMetadata.ReportInfo info;

	// TODO Consider remove.
	ReportMetadata.ReportCatalogue catalogue;
	ReportMetadata.ReportContent content;

	public ReportMedataBuilder reportId(String reportId) {
		this.reportId = reportId;
		return this;
	}

	public ReportMedataBuilder reportName(String reportName) {
		this.reportName = reportName;
		return this;
	}

	public ReportMedataBuilder templatePath(String templatePath) {
		this.templatePath = templatePath;
		return this;
	}


	public ReportMedataBuilder type(ReportEnums type) {
		this.reportType = type;
		return this;
	}

	public TitleBuilder partTitle() {
		return new TitleBuilder(this);
	}


	public final static class TitleBuilder {

		private ReportMedataBuilder builder;

		public TitleBuilder(ReportMedataBuilder builder) {
			this.builder = builder;
			this.builder.title = new ReportMetadata.ReportTitle();
		}

		public TitleBuilder background(String background) {
			this.builder.title.setBackground(background);
			return this;
		}

		public TitleBuilder title(String title) {
			this.builder.title.setTitle(title);
			return this;
		}

		public TitleBuilder description(String description) {
			this.builder.title.setDescription(description);
			return this;
		}

		public InfoBuilder partInfo() {
			return new InfoBuilder(this.builder);
		}

	}

	public final static class InfoBuilder {

		private ReportMedataBuilder builder;

		InfoBuilder(ReportMedataBuilder builder) {
			this.builder = builder;
			this.builder.info = new ReportMetadata.ReportInfo();
		}

		public InfoBuilder info(String label, String value) {
			this.builder.info.getLabels().put(label, value);
			return this;
		}

		public InfoBuilder info(LinkedHashMap<String, String> labels) {
			this.builder.info.getLabels().putAll(labels);
			return this;
		}

		public InfoBuilder image(String url) {
			this.builder.info.setImage(url);
			return this;
		}

		public CatalogueBuilder partCatalogue() {
			return new CatalogueBuilder(this.builder);
		}
	}

	public final static class CatalogueBuilder {

		private ReportMedataBuilder builder;

		public CatalogueBuilder(ReportMedataBuilder builder) {
			this.builder = builder;
			this.builder.catalogue = new ReportMetadata.ReportCatalogue();
		}

		public CatalogueBuilder catalogue(String chapter, List<String> indices) {
			this.builder.catalogue.getChapters().put(chapter, indices);
			return this;
		}

		public CatalogueBuilder catalogue(LinkedHashMap<String, List<String>> catalogues) {
			this.builder.catalogue.getChapters().putAll(catalogues);
			return this;
		}

		public ContentBuilder partContent() {
			return new ContentBuilder(this.builder);
		}
	}

	public final static class ContentBuilder {

		private ReportMedataBuilder builder;

		ContentBuilder(ReportMedataBuilder builder) {
			this.builder = builder;
			this.builder.content = new ReportMetadata.ReportContent();
		}

		public ContentBuilder content(String chapter, List<String> indices, List<String> description, List<ReportData> data) {
			this.builder.content.getChapter().add(chapter);
			this.builder.content.getIndices().add(indices);
			this.builder.content.getDescription().add(description);
			this.builder.content.getData().add(data);
			return this;
		}

		public ContentBuilder content(List<String> chapter, List<List<String>> indices, List<List<String>> description,
				List<List<ReportData>> data) {
			this.builder.content.getChapter().addAll(chapter);
			this.builder.content.getIndices().addAll(indices);
			this.builder.content.getDescription().addAll(description);
			this.builder.content.getData().addAll(data);
			return this;
		}

		public ReportMetadata build() {
			if ((builder.catalogue == null || builder.catalogue.getChapters().isEmpty()) && builder.content != null) {
				// dynamic generate catalogue.
				builder.catalogue = dynamicGenerateCatalogue(builder.content);
			}
			// Notice：there has two image that not generation but we need to handle it.
			if (StringUtils.isNotEmpty(builder.title.getBackground())) {

			}

			return new ReportMetadata(builder.reportId,
					builder.reportName,
					builder.reportType,
					builder.title,
					builder.info,
					builder.catalogue,
					builder.content);
		}

		/**
		 * Dynamic generate catalogue through content.
		 *
		 * @param content report content including text, echarts etc.
		 */
		private ReportMetadata.ReportCatalogue dynamicGenerateCatalogue(@NonNull ReportMetadata.ReportContent content) {
			Asserts.notNull(content, "Content is empty.");
			List<String> chapter = content.getChapter();
			List<List<String>> indices = content.getIndices();
			if (CollectionUtils.isEmpty(chapter) && CollectionUtils.isEmpty(indices)) {
				throw new ReportDynamicCatalogueException("Chapter and Indices both empty.");
			}
			ReportMetadata.ReportCatalogue catalogue = new ReportMetadata.ReportCatalogue();
			Map<String, List<String>> val = Maps.newLinkedHashMap();

			for (int i = 0; i < chapter.size(); i++) {
				val.put(chapter.get(i), indices.get(i));
			}

			catalogue.setChapters(val);
			return catalogue;
		}
	}
}
