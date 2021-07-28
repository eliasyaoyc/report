package yyc.open.framework.microants.components.kit.report;

/**
 * {@link MicroantsFileConstants} constants collection,e.g. {@link Image}, {@link File} etc...
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/7/28
 */
public interface MicroantsFileConstants {
    /**
     * Constants about image e.g bar, cross-bar etc.
     */
    interface Image {
        String BAR = "柱状图";
        String CROSS_BAR = "横向单柱状图";
        String CROSS_MULTI_BAR = "横向多柱状图";
        String LINE = "折线图";
        String PIE = "实心饼状图";
        String HOLLOW_PIE = "空心饼状图";
    }

    /**
     * Constants about file e.g. pdf, excel etc.
     */
    interface File {
        String PDF = "pdf";
        String EXCEL = "excel";
        String WORD = "word";
        String HTML = "html";
    }
}
