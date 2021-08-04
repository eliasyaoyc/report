package yyc.open.framework.microants.components.kit.report;

import org.junit.Test;
import yyc.open.framework.microants.components.kit.common.reflect.AnnotationScannerKit;
import yyc.open.framework.microants.components.kit.report.commons.Processor;
import yyc.open.framework.microants.components.kit.report.commons.ReportEnums;
import yyc.open.framework.microants.components.kit.report.entity.ReportData;

import java.lang.annotation.Annotation;
import java.util.*;

/**
 * {@link ReportTest}
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/8/2
 */
public class ReportTest {
    private ReportRuntime runtime;

    {
        runtime = new ReportRuntime();
        runtime.start();
    }

    private static ReportData barEcharts() {
        String[] name = {"苹果", "香蕉", "雪梨", "西瓜", "哈密瓜", "榴莲"};
        List<Object[]> names = new ArrayList<>();
        names.add(name);
        Integer[] value = {3364, 13899, 2181, 21798, 1796, 1300};
        return ReportData.echart("水果", ReportEnums.BAR, "水果图例", names, value);
    }

    private static ReportData crossBarEcharts() {
        String[] name = {"苹果", "香蕉", "雪梨", "西瓜", "哈密瓜", "榴莲"};
        List<Object[]> names = new ArrayList<>();
        names.add(name);
        Integer[] value = {3364, 13899, 2181, 21798, 1796, 1300};
        return ReportData.echart("水果", ReportEnums.CROSS_BAR, "水果图例", names, value);
    }

    private static ReportData lineEcharts() {
        Integer[] name = {43364, 13899, 12000, 2181, 21798, 1796, 1300};
        List<Object[]> names = new ArrayList<>();
        names.add(name);
        String[] value = {"A罩杯", "B罩杯", "C罩杯", "D罩杯", "E罩杯", "F罩杯", "G罩杯"};
        return ReportData.echart("胸罩使用人数", ReportEnums.LINE, "胸罩图例", names, value);
    }

    private static ReportData pieEcharts() {
        String[] name = {"A罩杯", "B罩杯", "C罩杯", "D罩杯", "E罩杯", "F罩杯", "G罩杯"};
        List<Object[]> names = new ArrayList<>();
        names.add(name);
        Integer[] value = {43364, 13899, 12000, 2181, 21798, 1796, 1300};
        return ReportData.echart("胸罩图例", ReportEnums.PIE, names, value);
    }

    @Test
    public void testHtml() {
        Report report = ReportBuilder.ofDefault();

        ReportMetadata metadata = ReportMetadata.builder()
                .type(ReportEnums.HTML)
                .partTitle().title("综合安全报表").description("Comprehensive Security Report").background("base64")
                .partInfo()
                .info("报告时间范围", "2017-07-01至2021-09-30")
                .info("报告生成时间", "2021-10-01 08:00:00")
                .partCatalogue()
                .catalogue("第一章 整体摘要", Arrays.asList("1.1 安全概览", "1.2 平台状态"))
                .catalogue("第二章 事件分析", Arrays.asList("2.1 告警类型占比", "2.2 威胁级别占比"))
                .partContent()
                .content("第N 章 我是标题",
                        Arrays.asList("1.1 总计（多）", "1.2 总计", "1.3 echarts 图表"),
                        Arrays.asList("我是组件描述", "我是组件描述", "我是组件描述"),
                        Arrays.asList(barEcharts(), crossBarEcharts(), lineEcharts(), pieEcharts())
                        )
                .build();

        report.generateReport(Arrays.asList(metadata));
    }

    @Test
    public void testLine(){
        Report report = ReportBuilder.ofDefault();


        ReportMetadata metadata = ReportMetadata.builder()
                .type(ReportEnums.HTML)
                .partTitle().title("综合安全报表").description("Comprehensive Security Report").background("base64")
                .partInfo()
                .info("报告时间范围", "2017-07-01至2021-09-30")
                .info("报告生成时间", "2021-10-01 08:00:00")
                .partCatalogue()
                .catalogue("第一章 整体摘要", Arrays.asList("1.1 安全概览", "1.2 平台状态"))
                .catalogue("第二章 事件分析", Arrays.asList("2.1 告警类型占比", "2.2 威胁级别占比"))
                .partContent()
                .content("第N 章 我是标题",
                        Arrays.asList("1.1 总计（多）", "1.2 总计", "1.3 echarts 图表"),
                        Arrays.asList("我是组件描述", "我是组件描述", "我是组件描述"),
                        Arrays.asList(lineEcharts())
                )
                .build();

        report.generateReport(Arrays.asList(metadata));
    }

    @Test
    public void testCrossBar() {
        Report report = ReportBuilder.ofDefault();

        ReportMetadata metadata = ReportMetadata.builder()
                .type(ReportEnums.HTML)
                .partTitle().title("综合安全报表").description("Comprehensive Security Report").background("base64")
                .partInfo()
                .info("报告时间范围", "2017-07-01至2021-09-30")
                .info("报告生成时间", "2021-10-01 08:00:00")
                .partCatalogue()
                .catalogue("第一章 整体摘要", Arrays.asList("1.1 安全概览", "1.2 平台状态"))
                .catalogue("第二章 事件分析", Arrays.asList("2.1 告警类型占比", "2.2 威胁级别占比"))
                .partContent()
                .content("第N 章 我是标题",
                        Arrays.asList("1.1 总计（多）", "1.2 总计", "1.3 echarts 图表"),
                        Arrays.asList("我是组件描述", "我是组件描述", "我是组件描述"),
                        Arrays.asList(crossBarEcharts()))
                .build();

        report.generateReport(Arrays.asList(metadata));
    }

    @Test
    public void testPdf() {
        Map<Class<? extends Annotation>, Set<Class<?>>> classSetMap = AnnotationScannerKit.scanClassesByAnnotations("yyc.open.framework.microants.components.kit.report", Processor.class);
        System.out.println(classSetMap);
    }

    @Test
    public void testWord() {
    }

    @Test
    public void testGenerateFail() {

    }

    @Test
    public void testReGenerate() {

    }
}
