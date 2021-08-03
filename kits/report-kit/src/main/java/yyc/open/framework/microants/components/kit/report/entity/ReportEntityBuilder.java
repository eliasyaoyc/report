package yyc.open.framework.microants.components.kit.report.entity;

import yyc.open.framework.microants.components.kit.report.commons.ReportEnums;

import java.util.List;

/**
 * {@link ReportEntityBuilder} The builder model for {@link ReportEntity}.
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/8/2
 */
public class ReportEntityBuilder {
    private String reportId;
    private String reportName;
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
        return new TitleBuilder();
    }

    public InfoBuilder partInfo() {
        return new InfoBuilder();
    }

    public CatalogueBuilder partCatalogue() {
        return new CatalogueBuilder();
    }

    public ContentBuilder partContent() {
        return new ContentBuilder();
    }

    public final static class TitleBuilder extends ReportEntityBuilder {

        TitleBuilder() {
            super.title = new ReportEntity.ReportTitle();
        }

        public TitleBuilder background(String background) {
            super.title.setBackground(background);
            return this;
        }

        public TitleBuilder title(String title) {
            super.title.setTitle(title);
            return this;
        }

        public TitleBuilder description(String description) {
            super.title.setDescription(description);
            return this;
        }
    }

    public final static class InfoBuilder extends ReportEntityBuilder {
        InfoBuilder() {
            super.info = new ReportEntity.ReportInfo();
        }

        public InfoBuilder info(String label, String value) {
            super.info.getLabels().add(label);
            super.info.getValues().add(value);
            return this;
        }

        public InfoBuilder info(List<String> labels, List<String> values) {
            super.info.getLabels().addAll(labels);
            super.info.getValues().addAll(values);
            return this;
        }
    }

    public final static class CatalogueBuilder extends ReportEntityBuilder {
        CatalogueBuilder() {
            super.catalogue = new ReportEntity.ReportCatalogue();
        }

        public CatalogueBuilder catalogue(String chapter, List<String> indices) {
            super.catalogue.getChapter().add(chapter);
            super.catalogue.getIndices().add(indices);
            return this;
        }

        public CatalogueBuilder catalogue(List<String> chapter, List<List<String>> indices) {
            super.catalogue.getChapter().addAll(chapter);
            super.catalogue.getIndices().addAll(indices);
            return this;
        }
    }

    public final static class ContentBuilder extends ReportEntityBuilder {
        ContentBuilder() {
            super.content = new ReportEntity.ReportContent();
        }

        public ContentBuilder content(String chapter, List<String> indices, List<String> description, List<ReportData> data) {
            super.content.getChapter().add(chapter);
            super.content.getIndices().add(indices);
            super.content.getDescription().add(description);
            super.content.getData().add(data);
            return this;
        }

        public ContentBuilder content(List<String> chapter, List<List<String>> indices, List<List<String>> description, List<List<ReportData>> data) {
            super.content.getChapter().addAll(chapter);
            super.content.getIndices().addAll(indices);
            super.content.getDescription().addAll(description);
            super.content.getData().addAll(data);
            return this;
        }
    }

    public ReportEntity build() {
        return new ReportEntity(reportId, reportName, reportType, title, info, catalogue, content);
    }
}
