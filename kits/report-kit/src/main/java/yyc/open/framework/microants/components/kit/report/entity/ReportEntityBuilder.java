package yyc.open.framework.microants.components.kit.report.entity;

import yyc.open.framework.microants.components.kit.common.uuid.UUIDsKit;
import yyc.open.framework.microants.components.kit.report.commons.ReportEnums;

import java.util.List;

/**
 * {@link ReportEntityBuilder} The builder model for {@link ReportEntity}.
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/8/2
 */
public class ReportEntityBuilder {
    private String reportId = UUIDsKit.base64UUID();
    private String reportName = UUIDsKit.base64UUID();

    private String templatePath;
    private ReportEnums reportType;
    ReportEntity.ReportTitle title;
    ReportEntity.ReportInfo info;

    // TODO Consider remove.
    ReportEntity.ReportCatalogue catalogue;
    ReportEntity.ReportContent content;

    public ReportEntityBuilder reportId(String reportId) {
        this.reportId = reportId;
        return this;
    }

    public ReportEntityBuilder reportName(String reportName) {
        this.reportName = reportName;
        return this;
    }

    public ReportEntityBuilder templatePath(String templatePath) {
        this.templatePath = templatePath;
        return this;
    }


    public ReportEntityBuilder type(ReportEnums type) {
        this.reportType = type;
        return this;
    }

    public TitleBuilder partTitle() {
        return new TitleBuilder(this);
    }


    public final static class TitleBuilder {
        private ReportEntityBuilder builder;

        public TitleBuilder(ReportEntityBuilder builder) {
            this.builder = builder;
            this.builder.title = new ReportEntity.ReportTitle();
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
        private ReportEntityBuilder builder;

        InfoBuilder(ReportEntityBuilder builder) {
            this.builder = builder;
            this.builder.info = new ReportEntity.ReportInfo();
        }

        public InfoBuilder info(String label, String value) {
            this.builder.info.getLabels().add(label);
            this.builder.info.getValues().add(value);
            return this;
        }

        public InfoBuilder info(List<String> labels, List<String> values) {
            this.builder.info.getLabels().addAll(labels);
            this.builder.info.getValues().addAll(values);
            return this;
        }

        public CatalogueBuilder partCatalogue() {
            return new CatalogueBuilder(this.builder);
        }
    }

    public final static class CatalogueBuilder {
        private ReportEntityBuilder builder;

        public CatalogueBuilder(ReportEntityBuilder builder) {
            this.builder = builder;
            this.builder.catalogue = new ReportEntity.ReportCatalogue();
        }

        public CatalogueBuilder catalogue(String chapter, List<String> indices) {
            this.builder.catalogue.getChapter().add(chapter);
            this.builder.catalogue.getIndices().add(indices);
            return this;
        }

        public CatalogueBuilder catalogue(List<String> chapter, List<List<String>> indices) {
            this.builder.catalogue.getChapter().addAll(chapter);
            this.builder.catalogue.getIndices().addAll(indices);
            return this;
        }

        public ContentBuilder partContent() {
            return new ContentBuilder(this.builder);
        }
    }

    public final static class ContentBuilder {
        private ReportEntityBuilder builder;

        ContentBuilder(ReportEntityBuilder builder) {
            this.builder = builder;
            this.builder.content = new ReportEntity.ReportContent();
        }

        public ContentBuilder content(String chapter, List<String> indices, List<String> description, List<ReportData> data) {
            this.builder.content.getChapter().add(chapter);
            this.builder.content.getIndices().add(indices);
            this.builder.content.getDescription().add(description);
            this.builder.content.getData().add(data);
            return this;
        }

        public ContentBuilder content(List<String> chapter, List<List<String>> indices, List<List<String>> description, List<List<ReportData>> data) {
            this.builder.content.getChapter().addAll(chapter);
            this.builder.content.getIndices().addAll(indices);
            this.builder.content.getDescription().addAll(description);
            this.builder.content.getData().addAll(data);
            return this;
        }

        public ReportEntity build() {
            return new ReportEntity(builder.reportId,
                    builder.reportName,
                    builder.reportType,
                    builder.title,
                    builder.info,
                    builder.catalogue,
                    builder.content);
        }
    }
}
