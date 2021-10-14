package yyc.open.component.report;

import static yyc.open.component.report.commons.file.FileKit.tempFile;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.data.DocumentRenderData;
import com.deepoove.poi.data.Documents;
import com.deepoove.poi.data.ParagraphRenderData;
import com.deepoove.poi.data.Paragraphs;
import com.deepoove.poi.data.Texts;
import com.deepoove.poi.data.style.ParagraphStyle;
import com.deepoove.poi.policy.DocumentRenderPolicy;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFStyle;
import org.apache.poi.xwpf.usermodel.XWPFStyles;
import org.junit.Test;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTDecimalNumber;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTOnOff;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTR;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTRPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSectPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTString;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTStyle;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STFldCharType;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STHdrFtr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STJc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STStyleType;
import yyc.open.component.report.commons.ReportEnums;
import yyc.open.component.report.commons.file.FileKit;
import yyc.open.component.report.entity.ReportData;

/**
 * {@link ReportTest}
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/8/2
 */
public class ReportTest {

	private ReportRuntime runtime;
	private static final String DIRECTION_URL = "/Users/eliasyao/Desktop/microants-components/kits/report-kit/src/main/resources/templates/img_directory.png";
	private static final String BACKGROUND_URL = "/Users/eliasyao/Desktop/microants-components/kits/report-kit/src/main/resources/templates/img_background.png";

	{
		runtime = new ReportRuntime();
		runtime.start(true);
	}

	private static ReportData barEcharts() {
		String[] name = {"苹果", "香蕉", "雪梨", "西瓜", "哈密瓜", "榴莲"};
		List<Object[]> names = new ArrayList<>();
		names.add(name);
		Integer[] value = {3364, 13899, 2181, 21798, 1796, 1300};
		return ReportData.echarts("水果", ReportEnums.BAR, "水果图例", names, value);
	}

	private static ReportData crossBarEcharts() {
		String[] name = {"苹果", "香蕉", "雪梨", "西瓜", "哈密瓜", "榴莲"};
		List<Object[]> names = new ArrayList<>();
		names.add(name);
		Integer[] value = {3364, 13899, 2181, 21798, 1796, 1300};
		return ReportData.echarts("水果", ReportEnums.CROSS_BAR, "水果图例", names, value);
	}

	private static ReportData lineEcharts() {
		Integer[] name = {43364, 13899, 12000, 2181, 21798, 1796, 1300};
		List<Object[]> names = new ArrayList<>();
		names.add(name);
		String[] value = {"A", "B", "C", "D", "E", "F", "G"};
		return ReportData.echarts("字母使用人数", ReportEnums.LINE, "字母图例", names, value);
	}

	private static ReportData lineEcharts1() {
		Integer[] name = {12000000, 11000000, 8000000, 1000000, 5000000, 1796, 12000000};
		List<Object[]> names = new ArrayList<>();
		names.add(name);
		String[] value = {"2021-04", "2021-05", "2021-06", "2021-07", "2021-08", "2021-09", "2021-10"};
		return ReportData.echarts("字母使用人数", ReportEnums.LINE, "字母图例", names, value);
	}

	private static ReportData lineEcharts2() {
		Integer[] name = {43364, 13899, 12000, 2181, 21798, 1796, 12000000};
		List<Object[]> names = new ArrayList<>();
		names.add(name);
		String[] value = {"2021-04", "2021-05", "2021-06", "2021-07", "2021-08", "2021-09", "2021-10"};
		return ReportData.echarts("字母使用人数", ReportEnums.LINE, "字母图例", names, value);
	}

	private static ReportData lineEcharts3() {
		Integer[] name = {43364, 13899, 12000, 2181, 21798, 1796, 12000000};
		List<Object[]> names = new ArrayList<>();
		names.add(name);
		String[] value = {"2021-04", "2021-05", "2021-06", "2021-07", "2021-08", "2021-09", "2021-10"};
		return ReportData.echarts("字母使用人数", ReportEnums.LINE, "字母图例", names, value);
	}

	private static ReportData pieEcharts() {
		String[] name = {"A", "B", "C", "D", "E", "F", "G"};
		List<Object[]> names = new ArrayList<>();
		names.add(name);
		Integer[] value = {43364, 13899, 12000, 2181, 21798, 1796, 1300};
		return ReportData.echarts("字母图例", ReportEnums.PIE, names, value);
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
				.partTitle().title("综合安全报表").description("").background(BACKGROUND_URL)
				.partInfo()
				.info("报告时间范围", "2017-07-01至2021-09-30")
				.info("报告生成时间", "2021-10-01 08:00:00")
				.image(DIRECTION_URL)
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
				.partTitle().title("综合安全报表").description("Comprehensive Security Report").background(BACKGROUND_URL)
				.partInfo()
				.info("报告时间范围", "2017-07-01至2021-09-30")
				.info("报告生成时间", "2021-10-01 08:00:00")
				.image(DIRECTION_URL)
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
				.partTitle().title("综合安全报表").description("Comprehensive Security Report").background(BACKGROUND_URL)
				.partInfo()
				.info("报告时间范围", "2017-07-01至2021-09-30")
				.info("报告生成时间", "2021-10-01 08:00:00")
				.image(DIRECTION_URL)
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
				.partTitle().title("综合安全报表").description("Comprehensive Security Report").background(BACKGROUND_URL)
				.partInfo()
				.info("报告时间范围", "2017-07-01至2021-09-30")
				.info("报告生成时间", "2021-10-01 08:00:00")
				.image(DIRECTION_URL)
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
				.partTitle().title("综合安全报表").description("Comprehensive Security Report").background(BACKGROUND_URL)
				.partInfo()
				.info("报告时间范围", "2017-07-01至2021-09-30")
				.info("报告生成时间", "2021-10-01 08:00:00")
				.image(DIRECTION_URL)
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
				.partTitle().title("综合安全报表").description("Comprehensive Security Report").background(BACKGROUND_URL)
				.partInfo()
				.info("报告时间范围", "2017-07-01至2021-09-30")
				.info("报告生成时间", "2021-10-01 08:00:00")
				.image(DIRECTION_URL)
				.partCatalogue()
				.catalogue("第一章 整体摘要", Arrays.asList("1.1 安全概览", "1.2 平台状态"))
				.catalogue("第二章 事件分析", Arrays.asList("2.1 告警类型占比", "2.2 威胁级别占比"))
				.partContent()
				.content("第N 章 我是标题",
						Arrays.asList("1.1 总计（多）", "1.2 总计", "1.3 echarts 图表"),
						Arrays.asList("我是组件描述", "我是组件描述", "我是组件描述"),
						Arrays.asList(lineEcharts1(), lineEcharts2(), lineEcharts3())
				)
				.build();

		report.generateReport(Arrays.asList(metadata));
	}

	@Test
	public void testCrossBar() {
		Report report = ReportBuilder.ofDefault();

		ReportMetadata metadata = ReportMetadata.builder()
				.type(ReportEnums.HTML)
				.partTitle().title("综合安全报表").description("Comprehensive Security Report").background(BACKGROUND_URL)
				.partInfo()
				.info("报告时间范围", "2017-07-01至2021-09-30")
				.info("报告生成时间", "2021-10-01 08:00:00")
				.image(DIRECTION_URL)
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
	public void testNoDataChart() {
		Report report = ReportBuilder.ofDefault();

		ReportMetadata metadata = ReportMetadata.builder()
				.type(ReportEnums.HTML)
				.partTitle().title("综合安全报表").description("Comprehensive Security Report").background(BACKGROUND_URL)
				.partInfo()
				.info("报告时间范围", "2017-07-01至2021-09-30")
				.info("报告生成时间", "2021-10-01 08:00:00")
				.image(DIRECTION_URL)
				.partCatalogue()
				.catalogue("第一章 整体摘要", Arrays.asList("1.1 安全概览", "1.2 平台状态"))
				.catalogue("第二章 事件分析", Arrays.asList("2.1 告警类型占比", "2.2 威胁级别占比"))
				.partContent()
				.content("第N 章 我是标题",
						Arrays.asList("1.1 总计（多）", "1.2 总计", "1.3 echarts 图表"),
						Arrays.asList("我是组件描述", "我是组件描述", "我是组件描述"),
						Arrays.asList(ReportData.echarts("水果", ReportEnums.CROSS_BAR, "水果图例", null, null), ReportData.echarts("水果",
								ReportEnums.CROSS_BAR, "水果图例", null, null), ReportData.echarts("水果", ReportEnums.CROSS_BAR, "水果图例", null, null)))
				.build();

		report.generateReport(Arrays.asList(metadata));
	}

	@Test
	public void test() throws IOException {
		String s = tempFile("templates/cover.docx");
		System.out.println(s);
//		writeTOC();
	}

	@Test
	public void testBg() throws Exception{
		XWPFDocument document = new XWPFDocument();
		XWPFParagraph paragraph = document.createParagraph();
		XWPFRun text = paragraph.createRun();
		text.setText("sad sad sad撒更好");
		text.setFontSize(40);
		text.addPicture(new FileInputStream("/Users/eliasyao/Desktop/背景图@1x.png"),6,"Generated", Units.toEMU(469),
				Units.toEMU(649));
//		XWPFRun run = paragraph.createRun();

		FileOutputStream out = new FileOutputStream(new File("/Users/eliasyao/Desktop/create_toc.docx"));

		document.write(out);
		out.close();
	}

	void generateWatermark(String watermark, String path) {
		InputStream is = null;
		OutputStream os = null;

		try {
			// 1. File source.
			Image srcImg = ImageIO.read(new File(path));

			BufferedImage buffImg = new BufferedImage(srcImg.getWidth(null), srcImg.getHeight(null), BufferedImage.TYPE_INT_RGB);
			// 2. Get the brush object.
			Graphics2D g = buffImg.createGraphics();
			// 3. Sets the jagged edge treatment for a line segment.
			g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			g.drawImage(srcImg.getScaledInstance(srcImg.getWidth(null), srcImg.getHeight(null), Image.SCALE_SMOOTH), 0, 0, null);
			// 4. Sets the watermark rotation.
			g.rotate(Math.toRadians(-15), buffImg.getWidth() / 2, buffImg.getHeight() / 2);
			// 5. Sets the watermark color.
			g.setColor(new Color(190, 190, 190));
			// 6. Sets the watermark front.
			g.setFont(new Font("宋体", Font.BOLD, buffImg.getHeight() / 6));
			// 7. Sets the watermark text transparency.
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 0.15f));
			// 8. The first parameter -> set the content, the last two parameters -> the coordinate position of the text on the image (x,y)
			g.drawString(watermark, buffImg.getWidth() / 4, buffImg.getHeight() / 2);
			// 9、Release resource.
			g.dispose();
			// 10. Generate.
			os = new FileOutputStream(path);
			ImageIO.write(buffImg, FileKit.suffix(path, '.'), os);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != is) {
					is.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				if (null != os) {
					os.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void writeTOC() throws IOException {
		XWPFDocument document = new XWPFDocument();
		createDefaultFooter(document);
		addCustomHeadingStyle(document, "Heading1", 1);
		addCustomHeadingStyle(document, "Heading2", 2);

		//Write the Document in file system
		FileOutputStream out = new FileOutputStream(new File("/Users/eliasyao/Desktop/create_toc.docx"));

		//添加标题
		XWPFParagraph titleParagraph = document.createParagraph();

		//设置段落居中
		titleParagraph.setAlignment(ParagraphAlignment.CENTER);

		XWPFRun titleParagraphRun = titleParagraph.createRun();
		titleParagraphRun.setText("Java PoI");
		titleParagraphRun.setColor("000000");
		titleParagraphRun.setFontSize(20);

		XWPFParagraph mulu = document.createParagraph();
		mulu.setPageBreak(true);
		mulu.setAlignment(ParagraphAlignment.CENTER);
		XWPFRun run2 = mulu.createRun();
		run2.getCTR().addNewLastRenderedPageBreak();
		run2.setText("目录");
		run2.setColor("000000");
		run2.setFontSize(20);

		XWPFParagraph mulu1 = document.createParagraph();
		XWPFRun run3 = mulu1.createRun();
		run3.setText("toc");

		//段落
		XWPFParagraph firstParagraph = document.createParagraph();
		firstParagraph.setPageBreak(true);
		firstParagraph.setStyle("Heading1");
		XWPFRun run = firstParagraph.createRun();
		run.getCTR().addNewLastRenderedPageBreak();
		run.setText("段落1。");
		run.setFontFamily("微软雅黑");
		run.setFontSize(22);

		//段落
		XWPFParagraph firstParagraph1 = document.createParagraph();
		firstParagraph1.setStyle("Heading2");
		XWPFRun run1 = firstParagraph1.createRun();
		run1.setText("段落2");
		run1.setFontFamily("微软雅黑");
		run1.setFontSize(15);

		//段落
		XWPFParagraph twoParagraph = document.createParagraph();
		twoParagraph.setPageBreak(true);
		twoParagraph.setStyle("Heading1");
		XWPFRun runa = twoParagraph.createRun();
		runa.getCTR().addNewLastRenderedPageBreak();
		runa.setText("段落2。");
		runa.setFontFamily("微软雅黑");
		runa.setFontSize(22);

		for (int i = 0; i < 30; i++) {
			XWPFParagraph twoParagraph1 = document.createParagraph();
			twoParagraph1.setStyle("Heading2");
			XWPFRun runa1 = twoParagraph1.createRun();
			runa1.setText("段落2");
			runa1.setFontFamily("微软雅黑");
			runa1.setFontSize(15);
		}


		//段落
		XWPFParagraph a = document.createParagraph();
		a.setPageBreak(true);
		a.setStyle("Heading1");
		XWPFRun a1 = a.createRun();
		a1.setText("段落1。");
		a1.setFontFamily("微软雅黑");
		a1.setFontSize(22);

		for (int i = 0; i < 20; i++) {
			XWPFParagraph b = document.createParagraph();
			b.setStyle("Heading2");
			XWPFRun b1 = b.createRun();
			b1.setText("段落2");
			b1.setFontFamily("微软雅黑");
			b1.setFontSize(15);
		}

		document.write(out);
		out.close();
	}

	/**
	 * 增加自定义标题样式。这里用的是stackoverflow的源码
	 *
	 * @param docxDocument 目标文档
	 * @param strStyleId   样式名称
	 * @param headingLevel 样式级别
	 */
	private static void addCustomHeadingStyle(XWPFDocument docxDocument, String strStyleId, int headingLevel) {

		CTStyle ctStyle = CTStyle.Factory.newInstance();
		ctStyle.setStyleId(strStyleId);

		CTString styleName = CTString.Factory.newInstance();
		styleName.setVal(strStyleId);
		ctStyle.setName(styleName);

		CTDecimalNumber indentNumber = CTDecimalNumber.Factory.newInstance();
		indentNumber.setVal(BigInteger.valueOf(headingLevel));

		// lower number > style is more prominent in the formats bar
		ctStyle.setUiPriority(indentNumber);

		CTOnOff onoffnull = CTOnOff.Factory.newInstance();
		ctStyle.setUnhideWhenUsed(onoffnull);

		// style shows up in the formats bar
		ctStyle.setQFormat(onoffnull);

		// style defines a heading of the given level
		CTPPr ppr = CTPPr.Factory.newInstance();
		ppr.setOutlineLvl(indentNumber);
		ctStyle.setPPr(ppr);

		XWPFStyle style = new XWPFStyle(ctStyle);

		// is a null op if already defined
		XWPFStyles styles = docxDocument.createStyles();

		style.setType(STStyleType.PARAGRAPH);
		styles.addStyle(style);

	}

	public static void createDefaultFooter(final XWPFDocument docx) {
		CTP pageNo = CTP.Factory.newInstance();
		XWPFParagraph footer = new XWPFParagraph(pageNo, docx);
		CTPPr begin = pageNo.addNewPPr();
		begin.addNewPStyle().setVal("1");
		begin.addNewJc().setVal(STJc.CENTER);
		pageNo.addNewR().addNewFldChar().setFldCharType(STFldCharType.BEGIN);
		pageNo.addNewR().addNewInstrText().setStringValue("PAGE   \\* MERGEFORMAT");
		pageNo.addNewR().addNewFldChar().setFldCharType(STFldCharType.SEPARATE);
		CTR end = pageNo.addNewR();
		CTRPr endRPr = end.addNewRPr();
		endRPr.addNewNoProof();
		end.addNewFldChar().setFldCharType(STFldCharType.END);
		CTSectPr sectPr = docx.getDocument().getBody().isSetSectPr() ? docx.getDocument().getBody().getSectPr() : docx.getDocument().getBody().addNewSectPr();
		XWPFHeaderFooterPolicy policy = new XWPFHeaderFooterPolicy(docx, sectPr);
		policy.createFooter(STHdrFtr.DEFAULT, new XWPFParagraph[] { footer });
	}
}