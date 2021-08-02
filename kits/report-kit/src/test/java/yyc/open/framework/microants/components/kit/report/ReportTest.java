package yyc.open.framework.microants.components.kit.report;

import org.junit.Test;

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
        report.generateReport(null);
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
