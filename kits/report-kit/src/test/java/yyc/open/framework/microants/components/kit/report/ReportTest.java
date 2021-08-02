package yyc.open.framework.microants.components.kit.report;

import org.junit.Test;
import yyc.open.framework.microants.components.kit.report.commons.ReportEnums;
import yyc.open.framework.microants.components.kit.report.entity.ReportEntity;

import java.util.Arrays;

/**
 * {@link ReportTest}
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/8/2
 */
public class ReportTest {
    private Report report;

    {
        report = ReportBuilder.ofDefault();
    }

    @Test
    public void testHtml() {
        ReportEntity entity = ReportEntity.builder()
                .type(ReportEnums.HTML)
                .partTitle().background("").title("").description("")
                .partInfo().info("", "")
                .partCatalogue().catalogue("", null)
                .partContent().content("", null, null, null)
                .build();
        report.generateReport(Arrays.asList(entity));
    }

    @Test
    public void testPdf() {
        report.generateReport(null);
    }

    @Test
    public void testWord() {
        report.generateReport(null);
    }

    @Test
    public void testGenerateFail() {

    }

    @Test
    public void testReGenerate() {

    }
}
