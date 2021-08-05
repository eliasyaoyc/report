package yyc.open.framework.microants.components.kit.thirdpart.xwpdt;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import yyc.open.framework.microants.components.kit.common.validate.Asserts;
import yyc.open.framework.microants.components.kit.common.validate.NonNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * {@link FastWordKit}
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/8/5
 */
public class FastWordKit {
    private static final Logger LOGGER = LoggerFactory.getLogger(FastWordKit.class);

    private final DocumentRenderData data;
    private final String outputPath;
    private final String fileName;

    public FastWordKit(@NonNull DocumentRenderData data, @NonNull String outputPath, @NonNull String fileName) {
        this.data = data;
        this.outputPath = outputPath;
        this.fileName = fileName;
    }

    public static FastWordBuilder builder() {
        return new FastWordBuilder();
    }

    public static class FastWordBuilder {
        private Documents.DocumentBuilder documentBuilder;
        private String outputPath;
        private String fileName;

        public FastWordBuilder() {
            documentBuilder = Documents.of();
        }

        public FastWordBuilder smallText(@NonNull String content) {
            text(content, "000000", "Microsoft YaHei", 12d, false);
            return this;
        }

        public FastWordBuilder smallText(@NonNull String content, @NonNull boolean isCenter) {
            text(content, "000000", "Microsoft YaHei", 12d, isCenter);
            return this;
        }

        public FastWordBuilder middleText(@NonNull String content) {
            text(content, "000000", "Microsoft YaHei", 16d, false);
            return this;
        }

        public FastWordBuilder middleText(@NonNull String content, @NonNull boolean isCenter) {
            text(content, "000000", "Microsoft YaHei", 16d, isCenter);
            return this;
        }

        public FastWordBuilder bigText(@NonNull String content) {
            text(content, "000000", "Microsoft YaHei", 22d, false);
            return this;
        }

        public FastWordBuilder bigText(@NonNull String content, @NonNull boolean isCenter) {
            text(content, "000000", "Microsoft YaHei", 22d, isCenter);
            return this;
        }

        public FastWordBuilder text(@NonNull String content) {
            text(content, "000000", "Microsoft YaHei", 14d, false);
            return this;
        }

        public FastWordBuilder text(@NonNull String content, Double size) {
            text(content, "000000", "Microsoft YaHei", size, false);
            return this;
        }

        public FastWordBuilder text(@NonNull String content, @NonNull boolean isCenter) {
            text(content, "000000", "Microsoft YaHei", 14d, isCenter);
            return this;
        }

        public FastWordBuilder text(String content, String color, String front, Double size, boolean isCenter) {
            ParagraphRenderData paragraph = isCenter ? Paragraphs.of(Texts.of(content).fontFamily(front).fontSize(size).color(color).create()).center().create() :
                    Paragraphs.of(Texts.of(content).fontFamily(front).fontSize(size).color(color).create()).create();
            this.documentBuilder.addParagraph(paragraph);
            return this;
        }

        public FastWordBuilder table(List<List<String>> rows, boolean isCenter) {
            List<RowRenderData> ret = new ArrayList<>();

            if (rows.size() > 1) {
                List<String> remove = rows.remove(1);
                String[] cont = remove.toArray(new String[remove.size()]);
                RowRenderData data = Rows.of(cont).textColor("FFFFFF").bgColor("4472C4").center().create();
                ret.add(data);
            }

            rows.stream().forEach(row -> {
                String[] cont = row.toArray(new String[row.size()]);
                RowRenderData data = Rows.of(cont).center().create();
                ret.add(data);
            });

            TableRenderData table = isCenter ? Tables.of(ret.toArray(new RowRenderData[ret.size()])).autoWidth().center().create() :
                    Tables.of(ret.toArray(new RowRenderData[ret.size()])).autoWidth().create();

            this.documentBuilder.addTable(table);
            return this;
        }


        public FastWordBuilder image(String url, PictureType type) {
            try {
                PictureRenderData background = Pictures.ofStream(new FileInputStream(url), type)
                        .center()
                        .fitSize()
                        .create();
                this.documentBuilder.addParagraph(Paragraphs.of(background).create());
                this.blank();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            return this;
        }

        public FastWordBuilder blank() {
            this.documentBuilder.addParagraph(Paragraphs.of().create());
            return this;
        }

        public FastWordBuilder outputPath(String outputPath) {
            this.outputPath = outputPath;
            return this;
        }

        public FastWordBuilder fileName(String fileName) {
            this.fileName = fileName;
            return this;
        }


        public FastWordKit build() {
            return new FastWordKit(documentBuilder.create(), outputPath, fileName);
        }
    }


    public void create() {
        Asserts.state(StringUtils.isNotEmpty(outputPath) && StringUtils.isNotEmpty(fileName), "OutputPath & FileName");
        Asserts.state(fileName.contains(".docx") || fileName.contains(".doc"), "Word");
        File file = new File(outputPath + "/" + fileName);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        if (Objects.isNull(data)) {
            return;
        }
        XWPFTemplate template = XWPFTemplate.create(data);
        try {
            template.writeAndClose(new FileOutputStream(file));
        } catch (Exception e) {
            LOGGER.error("[FastWordKit] encountered error: {}", e);
        }
    }
}
