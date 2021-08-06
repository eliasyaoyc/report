# Report Kit

This kit support that generate echart picture(e.g. bar, line, pie etc.) and file creation.

# Supports

* Multiple chart generation(e.g. Bar、Cross Bar、Pie、Hollow Pie、Line etc).
* Multiple file generation(e.g. Excel、Html、Pdf、Word etc).
* Task status(e.g. progress).
* Report generation failure retry mechanism.
* Support HTML convert to PDF.

# Example

```java
import yyc.open.framework.microants.components.kit.report.ReportRuntime;

public class ReportTest {
    // 1. Create report runtime.
    private ReportRuntime runtime;

    {
        this.runtime = new ReportRuntime().start();
    }

    public void html() {
        // 2. Build report.
        Report report = ReportBuilder.ofDefault();

        // 3. Assemble data.
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

        // 4. Start to generate.
        report.generateReport(Arrays.asList(metadata));
    }
}
```

# TODO List

- [ ] Alarm module that supports enterprise wechat alarm and email.
- [x] Percentage complete of report generation.
- [ ] Parallel report generation.
- [x] Generate from fail-point.
- [x] Improved fault tolerance for report generation failures.
- [ ] Optimize the process by which users assemble report data.
- [ ] Optimize the same image generation repeatedly.(Checksum).

# Use-case

The concrete use-case, you can see test package that including three sub package

* Charts: all cases of generating diagrams(e.g. bar, line, pie etc.)
* Files: all cases of generating files(e.g. excel, word, html etc.)
* Combination: The combination generating the two above

# License

Licensed under of either of

* MIT license ([LICENSE-MIT](./LICENSE-MIT) or http://opensource.org/licenses/MIT)

<img src="https://github-readme-svg.vercel.app/api/v1/svg/road?cartype=normal&p=center" alt="My endless road" />