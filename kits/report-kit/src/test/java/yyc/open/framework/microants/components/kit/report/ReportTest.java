package yyc.open.framework.microants.components.kit.report;

import org.junit.Test;
import yyc.open.framework.microants.components.kit.report.commons.ReportEnums;
import yyc.open.framework.microants.components.kit.report.entity.ReportData;

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
        String[] value = {"A", "B", "C", "D", "E", "F", "G"};
        return ReportData.echart("字母使用人数", ReportEnums.LINE, "字母图例", names, value);
    }

    private static ReportData pieEcharts() {
        String[] name = {"A", "B", "C", "D", "E", "F", "G"};
        List<Object[]> names = new ArrayList<>();
        names.add(name);
        Integer[] value = {43364, 13899, 12000, 2181, 21798, 1796, 1300};
        return ReportData.echart("字母图例", ReportEnums.PIE, names, value);
    }

    private static ReportData multiTotal() {
        Map<String, Object> stat = new LinkedHashMap<>();
        stat.put("总警告", 1020);
        stat.put("攻击成功", 102);
        stat.put("外部攻击", 820);
        stat.put("外联攻击", 102);
        stat.put("横向攻击", 98);
        return ReportData.statistics(stat);
    }

    private static ReportData total() {
        Map<String, Object> stat = new LinkedHashMap<>();
        stat.put("总量", 1020);
        return ReportData.statistics(stat, "攻击成功数量102（10%）");
    }

    public static ReportData image() {
        return ReportData.image("/Users/eliasyao/Desktop/echarts.png");
    }

    public static ReportData tables() {
        List<List<String>> tables = new ArrayList<>();
        tables.add(Arrays.asList("接口名称", "状态", "类型", "链路模式", "接受流量"));
        tables.add(Arrays.asList("ens132", "连接中", "电口", "1000 Mbs", "131.21 kbps"));
        tables.add(Arrays.asList("ens133", "连接中", "电口", "1000 Mbs", "231.21 kbps"));
        tables.add(Arrays.asList("ens134", "连接中", "电口", "1000 Mbs", "631.21 kbps"));
        return ReportData.table(tables);
    }

    public static ReportData text() {
        List<String> text = new ArrayList<>();
        text.add("1、公网网络边界部署抗DDOS、防火墙、WAF类网络防护设备，并将需要保护的资产添加至安全防护策略中，若已部署该类设备，请检查覆盖范围是否已涵盖所有资产。");
        text.add("2、内网网络边界增加内部隔离机制，发现横向攻击后能及时将恶意软件、恶意行为进行拦截，阻止内部威胁的横向传播。");
        text.add("3、办公终端网络建议部署防病毒、EDR、邮件安全网关类设备，降低被病毒感染、邮件钓鱼等网络攻击成功机率。");
        text.add("4、定期进行安全基线检查及漏洞扫描检查，及时修复安全漏洞，避免被黑客利用系统漏洞进行攻击。");
        text.add("5、制定并执行资产上架规范，控制业务开放端口及账号权限，定期对资产进行梳理，杜绝法外资产情况。");
        text.add("6、制定并执行应急响应机制，及时感知、处理、响应安全事件。");
        return ReportData.text(text);
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
                        Arrays.asList("1.1 总计（多）", "1.2 总计", "1.4 表格", "1.5 文字建议", "1.6 柱状图", "1.7 横向柱状图", "1.8 折线图", "1.9 饼图"),
                        Arrays.asList("我是组件描述", "我是组件描述", "我是组件描述", "我是组件描述"),
                        Arrays.asList(multiTotal(), total(), tables(), text(), barEcharts(), crossBarEcharts(), lineEcharts(), pieEcharts())
                )
                .build();

        report.generateReport(Arrays.asList(metadata));
    }

    @Test
    public void testWord() {
        Report report = ReportBuilder.ofDefault();
        ReportMetadata metadata = ReportMetadata.builder()
                .type(ReportEnums.WORD)
                .partTitle().title("综合安全报表").description("Comprehensive Security Report").background("/Users/eliasyao/Desktop/img_background.png")
                .partInfo()
                .info("报告时间范围", "2017-07-01至2021-09-30")
                .info("报告生成时间", "2021-10-01 08:00:00")
                .partCatalogue()
                .catalogue("第一章 整体摘要", Arrays.asList("1.1 安全概览", "1.2 平台状态"))
                .catalogue("第二章 事件分析", Arrays.asList("2.1 告警类型占比", "2.2 威胁级别占比"))
                .partContent()
                .content("第N 章 我是标题",
                        Arrays.asList("1.1 总计（多）", "1.2 总计", "1.4 表格", "1.5 文字建议", "1.6 柱状图", "1.7 横向柱状图", "1.8 折线图", "1.9 饼图"),
                        Arrays.asList("我是组件描述", "我是组件描述", "我是组件描述", "我是组件描述"),
                        Arrays.asList(multiTotal(), total(), tables(), text(), barEcharts(), crossBarEcharts(), lineEcharts(), pieEcharts())
                )
                .build();

        report.generateReport(Arrays.asList(metadata));
    }

    @Test
    public void testPdf() {
        Report report = ReportBuilder.ofDefault();
        ReportMetadata metadata = ReportMetadata.builder()
                .type(ReportEnums.PDF)
                .partTitle().title("综合安全报表").description("Comprehensive Security Report").background("/Users/eliasyao/Desktop/img_background.png")
                .partInfo()
                .info("报告时间范围", "2017-07-01至2021-09-30")
                .info("报告生成时间", "2021-10-01 08:00:00")
                .partCatalogue()
                .catalogue("第一章 整体摘要", Arrays.asList("1.1 安全概览", "1.2 平台状态"))
                .catalogue("第二章 事件分析", Arrays.asList("2.1 告警类型占比", "2.2 威胁级别占比"))
                .partContent()
                .content("第N 章 我是标题",
                        Arrays.asList("1.1 总计（多）", "1.2 总计", "1.4 表格", "1.5 文字建议", "1.6 柱状图", "1.7 横向柱状图", "1.8 折线图", "1.9 饼图"),
                        Arrays.asList("我是组件描述", "我是组件描述", "我是组件描述", "我是组件描述"),
                        Arrays.asList(multiTotal(), total(), tables(), text(), barEcharts(), crossBarEcharts(), lineEcharts(), pieEcharts())
                )
                .build();

        report.generateReport(Arrays.asList(metadata));
    }

    @Test
    public void testAllEchartsHTML() {
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
                        Arrays.asList("1.1 总计（多）", "1.2 总计", "1.3 echarts 图表", "1.4 饼图"),
                        Arrays.asList("我是组件描述", "我是组件描述", "我是组件描述", "我是饼图"),
                        Arrays.asList(barEcharts(), crossBarEcharts(), lineEcharts(), pieEcharts())
                )
                .build();

        report.generateReport(Arrays.asList(metadata));
    }

    @Test
    public void testAllEchartsWord() {
        Report report = ReportBuilder.ofDefault();

        ReportMetadata metadata = ReportMetadata.builder()
                .type(ReportEnums.WORD)
                .partTitle().title("综合安全报表").description("Comprehensive Security Report").background("/Users/eliasyao/Desktop/img_background.png")
                .partInfo()
                .info("报告时间范围", "2017-07-01至2021-09-30")
                .info("报告生成时间", "2021-10-01 08:00:00")
                .partCatalogue()
                .catalogue("第一章 整体摘要", Arrays.asList("1.1 安全概览", "1.2 平台状态"))
                .catalogue("第二章 事件分析", Arrays.asList("2.1 告警类型占比", "2.2 威胁级别占比"))
                .partContent()
                .content("第N 章 我是标题",
                        Arrays.asList("1.1 总计（多）", "1.2 总计", "1.3 echarts 图表", "1.4 饼图"),
                        Arrays.asList("我是组件描述", "我是组件描述", "我是组件描述", "我是饼图"),
                        Arrays.asList(barEcharts(), crossBarEcharts(), lineEcharts(), pieEcharts())
                )
                .build();

        report.generateReport(Arrays.asList(metadata));
    }

    @Test
    public void testLine() {
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
    public void testGenerateFail() {

    }

    @Test
    public void testReGenerate() {

    }
}