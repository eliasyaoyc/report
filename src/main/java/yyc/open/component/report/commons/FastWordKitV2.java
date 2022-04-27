package yyc.open.component.report.commons;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.DocumentRenderData;
import com.deepoove.poi.data.Documents;
import com.deepoove.poi.data.ParagraphRenderData;
import com.deepoove.poi.data.Paragraphs;
import com.deepoove.poi.data.PictureRenderData;
import com.deepoove.poi.data.PictureType;
import com.deepoove.poi.data.Pictures;
import com.deepoove.poi.data.RowRenderData;
import com.deepoove.poi.data.Rows;
import com.deepoove.poi.data.Tables;
import com.deepoove.poi.data.Texts;
import com.deepoove.poi.util.BufferedImageUtils;
import com.deepoove.poi.util.ByteUtils;
import com.deepoove.poi.xwpf.XWPFParagraphWrapper;
import com.deepoove.poi.xwpf.XWPFShadingPattern;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.stream.Collectors;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.util.LocaleUtil;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy;
import org.apache.poi.xwpf.usermodel.IBodyElement;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFFooter;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFStyle;
import org.apache.poi.xwpf.usermodel.XWPFStyles;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.impl.xb.xmlschema.SpaceAttribute;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTBookmark;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTDecimalNumber;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTEmpty;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTHyperlink;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTJc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTOnOff;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTParaRPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTR;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTRPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSectPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTShd;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSimpleField;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTString;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTStyle;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTabStop;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTabs;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTbl;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTcPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTText;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STFldCharType;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STHdrFtr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STJc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STOnOff;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STShd;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STStyleType;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STTabJc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STTabTlc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import yyc.open.component.report.ReportMetadata;
import yyc.open.component.report.commons.FastWordKit.FastWordBuilder;
import yyc.open.component.report.commons.file.FileKit;
import yyc.open.component.report.commons.validate.Asserts;
import yyc.open.component.report.commons.validate.NonNull;

/**
 * {@link FastWordKitV2}
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/8/5
 */
public class FastWordKitV2 {

	private static final Logger LOGGER = LoggerFactory.getLogger(FastWordKitV2.class);

	private static final String DEFAULT_FRONT = "思源黑体";
	private final XWPFDocument document;
	private final String outputPath;
	private final String fileName;
	private final boolean autoGenerate;

	public FastWordKitV2(ReportMetadata metadata, boolean autoGenerate) {
		this.outputPath = metadata.getPath();
		this.fileName = metadata.getReportId() + ".docx";
		this.autoGenerate = autoGenerate;
		this.document = init(metadata);
	}

	private XWPFDocument init(ReportMetadata metadata) {
		try {
			File file = new File(outputPath + "/" + fileName);

			generateCover(file, metadata);

			XWPFDocument document = new XWPFDocument(new FileInputStream(file));

			addCustomHeadingStyle(document, "Heading1", 1);
			addCustomHeadingStyle(document, "Heading2", 2);
			createDefaultFooter(document);
			return document;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


	private void generateCover(File file, ReportMetadata metadata) throws Exception {
		Map<String, String> data = new HashMap<>();
		data.put("title", metadata.getTitle().getTitle());
		if (metadata.getInfo().getLabels() != null && metadata.getInfo().getLabels().size() == 2) {
			int index = 1;
			for (Entry<String, String> entry : metadata.getInfo().getLabels().entrySet()) {
				data.put("createkey" + index, entry.getKey());
				data.put("createvalue" + index, entry.getValue());
				index++;
			}
		}

		String tmp = FileKit.tempFile("templates/cover.docx");

		XWPFTemplate compile = XWPFTemplate.compile(new FileInputStream(tmp)).render(data);

		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}

		OutputStream out = new FileOutputStream(file);
		compile.write(out);
		out.close();
	}

	public void title(String content) {
		XWPFParagraph titleParagraph = document.createParagraph();
		titleParagraph.setPageBreak(true);
		titleParagraph.setAlignment(ParagraphAlignment.CENTER);
		XWPFRun titleParagraphRun = titleParagraph.createRun();
		titleParagraphRun.setText(content);
		titleParagraphRun.setFontSize(36);
		titleParagraphRun.setFontFamily(DEFAULT_FRONT);
	}

	public void image(String url, int width, int height) {
		XWPFParagraph firstParagraph = document.createParagraph();
		XWPFRun run = firstParagraph.createRun();
		try {
			run.addPicture(new FileInputStream(url), 6, "Generated", Units.toEMU(width), Units.toEMU(height));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void mulu() {
		blank();
		document.createParagraph().createRun().setText("toc");
	}

	public void blank() {
		XWPFParagraph xwpfParagraph = document.createParagraph();
		xwpfParagraph.createRun().setText("");
	}

	public void table(List<List<String>> rows) {
		XWPFParagraph paragraph = document.createParagraph();
		XmlCursor cursor = paragraph.getCTP().newCursor();
		XWPFTable table = document.insertNewTbl(cursor);

		// create first row.
		List<String> firstCell = rows.get(0);
		XWPFTableRow row = table.getRow(0);
		XWPFTableCell cell1 = row.getCell(0);
		cell1.setText(firstCell.get(0));
		cell1.setColor("4472C4");
		for (int i = 1; i < firstCell.size(); i++) {
			XWPFTableCell cell = row.addNewTableCell();
			cell.setText(firstCell.get(i));
			cell.setColor("4472C4");
		}

		for (int i = 1; i < rows.size(); i++) {
			XWPFTableRow row1 = table.createRow();
			List<String> cell = rows.get(i);
			for (int j = 0; j < cell.size(); j++) {
				row1.getCell(j).setText(cell.get(j));
			}
		}

		setTableLocation(table, "left");
		setCellLocation(table, "CENTER", "center");
		document.removeBodyElement(document.getPosOfParagraph(paragraph));
	}

	public void text(String content, String color, int size) {
		text(content, color, size, false);
	}

	public void text(String content, String color, int size, boolean isCenter) {
		XWPFParagraph firstParagraph = document.createParagraph();
		if (isCenter) {
			firstParagraph.setAlignment(ParagraphAlignment.CENTER);
		}
		XWPFRun run = firstParagraph.createRun();
		run.setText(content);
		run.setFontFamily(DEFAULT_FRONT);
		run.setColor(color);
		run.setFontSize(size);
	}


	public void heading1(String content) {
		XWPFParagraph firstParagraph = document.createParagraph();
		firstParagraph.setPageBreak(true);
		firstParagraph.setStyle("Heading1");
		XWPFRun run = firstParagraph.createRun();
		run.setText(content);
		run.setFontFamily(DEFAULT_FRONT);
		run.setFontSize(22);
	}

	public void heading2(String content) {
		XWPFParagraph firstParagraph = document.createParagraph();
		firstParagraph.setStyle("Heading2");
		XWPFRun run = firstParagraph.createRun();
		run.setText(content);
		run.setFontFamily(DEFAULT_FRONT);
		run.setFontSize(15);
	}

	private static void setCellLocation(XWPFTable xwpfTable, String verticalLoction, String horizontalLocation) {
		xwpfTable.setWidth(60000);
		List<XWPFTableRow> rows = xwpfTable.getRows();
		boolean isFirst = false;
		if (rows.size() > 1) {
			isFirst = true;
		}
		for (XWPFTableRow row : rows) {
			List<XWPFTableCell> cells = row.getTableCells();
			for (XWPFTableCell cell : cells) {
				CTTc cttc = cell.getCTTc();
				CTP ctp = cttc.getPList().get(0);
				CTPPr ctppr = ctp.getPPr();
				if (ctppr == null) {
					ctppr = ctp.addNewPPr();
				}

				CTJc ctjc = ctppr.getJc();
				if (ctjc == null) {
					ctjc = ctppr.addNewJc();
				}

				ctjc.setVal(STJc.Enum.forString(horizontalLocation)); //水平居中
				cell.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.valueOf(verticalLoction));//垂直居中
			}
		}
	}

	private static void setTableLocation(XWPFTable xwpfTable, String location) {
		CTTbl cttbl = xwpfTable.getCTTbl();
		CTTblPr tblpr = cttbl.getTblPr() == null ? cttbl.addNewTblPr() : cttbl.getTblPr();
		CTJc cTJc = tblpr.addNewJc();
		cTJc.setVal(STJc.Enum.forString(location));
	}

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


	private static void createDefaultFooter(final XWPFDocument docx) {
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
		CTSectPr sectPr = docx.getDocument().getBody().isSetSectPr() ? docx.getDocument().getBody().getSectPr()
				: docx.getDocument().getBody().addNewSectPr();
		XWPFHeaderFooterPolicy policy = new XWPFHeaderFooterPolicy(docx, sectPr);
		policy.createFooter(STHdrFtr.DEFAULT, new XWPFParagraph[]{footer});
	}

	public void create() throws Exception {
		Asserts.state(StringUtils.isNotEmpty(outputPath) && StringUtils.isNotEmpty(fileName), "OutputPath & FileName");
		Asserts.state(fileName.contains(".docx") || fileName.contains(".doc"), "Word");
		File file = new File(outputPath + "/" + fileName);
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		if (Objects.isNull(document)) {
			return;
		}
		FileOutputStream outputStream = null;
		try {
			outputStream = new FileOutputStream(file);
			if (autoGenerate) {
				automaticGenerateTOC(10, "toc", document, 0);
			} else {
				handGenerateTOC(document, "toc");
			}

			document.write(outputStream);
		} catch (Exception e) {
			LOGGER.error("[FastWordKit] encountered error.", e);
		} finally {
			try {
				if (outputStream != null) {
					outputStream.close();
				}
				if (document != null) {
					document.close();
				}
			} catch (Exception ex) {
				// ignore.
			}
		}
	}

	private static void handGenerateTOC(XWPFDocument document, String placeholder) {
		boolean findFlag = false;
		List<XWPFParagraph> list = document.getParagraphs();
		for (int i = 0; i < list.size() && !findFlag; i++) {
			XWPFParagraph p = list.get(i);
			String text = p.getParagraphText();
			if (text != null && text.toLowerCase().contains(placeholder.toLowerCase())) {
				List<XWPFRun> runList = p.getRuns();
				for (int j = 0; j < runList.size(); j++) {
					XWPFRun r = runList.get(j);
					r.setText("", 0);
				}
				CTSimpleField ctSimpleField = p.getCTP().addNewFldSimple();
				ctSimpleField.setInstr("TOC \\o \"1-3\" \\h \\z \\u");
				ctSimpleField.setDirty(STOnOff.TRUE);
				break;
			}
		}
	}

	private static void automaticGenerateTOC(int maxLevel, String contentFlag, XWPFDocument document, int fromPage) {
		XWPFParagraph p = null;
		boolean flag = false;
		for (int i = 0; i < document.getParagraphs().size(); i++) {
			p = document.getParagraphs().get(i);
			String text = p.getParagraphText();
			if (text != null && text.toLowerCase().contains(contentFlag.toLowerCase())) {
				flag = true;
				break;
			}
		}
		if (flag) {
			List<XWPFParagraph> list = new ArrayList<>();
			Iterator var3 = document.getParagraphs().iterator();
			List<IBodyElement> bodyElementList = document.getBodyElements();
			while (var3.hasNext()) {
				XWPFParagraph paragraph = (XWPFParagraph) var3.next();
				String parStyle = paragraph.getStyle();
				if (parStyle != null) {
					Integer level = null;
					if (StringUtils.isNumeric(parStyle)) {
						level = Integer.parseInt(parStyle);
					} else if (parStyle.startsWith("Heading")) {
						level = Integer.parseInt(parStyle.replace("Heading", ""));
					}

					if (level != null && level <= maxLevel) {
						list.add(paragraph);
					}
				}
			}
			if (list.size() > 0) {
				XWPFParagraph currentP = p;
				int[] chapterArr = new int[maxLevel];
				Arrays.fill(chapterArr, 0);
				for (int j = 0; j < list.size(); j++) {
					XWPFParagraph paragraph = list.get(j);
					String parStyle = paragraph.getStyle();
					if (parStyle != null) {
						Integer level = null;
						if (StringUtils.isNumeric(parStyle)) {
							level = Integer.parseInt(parStyle);
						} else if (parStyle.startsWith("Heading")) {
							level = Integer.parseInt(parStyle.replace("Heading", ""));
						}
						for (int k = level; k < maxLevel; k++) {
							chapterArr[k] = 0;
						}
						chapterArr[level - 1] = chapterArr[level - 1] + 1;
						// 得到数组对应的字符串值，同时去掉有.0的内容
						String s = Arrays.toString(chapterArr);
						String chapterArrStr = s.substring(1, s.length() - 1).replaceAll(" ", "").replaceAll(",", ".");
						while (chapterArrStr.endsWith(".0")) {
							chapterArrStr = chapterArrStr.substring(0, chapterArrStr.lastIndexOf(".0"));
						}

						List<CTBookmark> bookmarkList = paragraph.getCTP().getBookmarkStartList();
						String title = paragraph.getText();
						int pageBreakNum = pageBreak(paragraph, title, bodyElementList);
						// 前面多少页不算
						pageBreakNum = pageBreakNum - fromPage;
						XmlCursor cursor = currentP.getCTP().newCursor();
						XWPFParagraph newPara = document.insertNewParagraph(cursor);
						// 有可能bookMarkStart与标题不在同一段落中
						String bookName = "";
						if (bookmarkList != null && bookmarkList.size() > 0) {
							bookName = bookmarkList.get(0).getName();
						}
						addRow(maxLevel, chapterArrStr, j, newPara, level, paragraph.getText(), pageBreakNum, bookName);
					}
				}
			}
			// 清空当前段落的内容
			List<XWPFRun> runList = p.getRuns();
			for (int j = 0; j < runList.size(); j++) {
				XWPFRun r = runList.get(j);
				r.setText("", 0);
			}

			//p.getCTP().set(null);
			/*int index = document.getPosOfParagraph(p);
			document.removeBodyElement(index);*/
		}
	}

	private static void addRow(int maxLevel, String chapterNum, int index, XWPFParagraph newPara, int level, String title, int page,
			String bookmarkRef) {
		CTP p = newPara.getCTP();
		// 设置标题tab等级
		p.setRsidRDefault("00EF7E24".getBytes(LocaleUtil.CHARSET_1252));
		CTPPr pPr = p.addNewPPr();
		pPr.addNewPStyle().setVal(String.valueOf((10 * level)));
		CTTabs tabs = pPr.addNewTabs();

		CTTabStop tab = tabs.addNewTab();
		tab.setVal(STTabJc.RIGHT);
		tab.setLeader(STTabTlc.DOT);
		tab.setPos(new BigInteger("8296"));
		CTParaRPr paraRPr = pPr.addNewRPr();
		paraRPr.addNewNoProof();

		// 如果是第一项，需要设置目录结构，有时候不起作用
		/*if(index == 0) {
			p.addNewR().addNewFldChar().setFldCharType(STFldCharType.BEGIN);
			CTR run = p.addNewR();
			run.addNewRPr().addNewNoProof();
			CTText text = run.addNewInstrText();
			text.setSpace(SpaceAttribute.Space.PRESERVE);
			text.setStringValue("TOC \\o \"1-"+maxLevel+"\" \\h \\z \\u");
			run = p.addNewR();
			run.addNewRPr().addNewNoProof();
			run.addNewFldChar().setFldCharType(STFldCharType.SEPARATE);
		}*/

		// 添加链接导航
		CTHyperlink hyperlink = p.addNewHyperlink();
		hyperlink.setAnchor(bookmarkRef);
		hyperlink.setHistory(STOnOff.X_1);

		// 添加标题对应的序号
		/*CTR run = hyperlink.addNewR();
		CTRPr rPr = run.addNewRPr();
		rPr.addNewRStyle().setVal("ad");
		rPr.addNewNoProof();
		run.addNewT().setStringValue(chapterNum.toString());
		run = hyperlink.addNewR();
		rPr = run.addNewRPr();
		rPr.addNewSz().setVal(new BigInteger("21"));
		rPr.addNewSzCs().setVal(new BigInteger("22"));
		rPr.addNewNoProof();
		run.addNewTab();*/

		CTR run = hyperlink.addNewR();
		run.addNewRPr().addNewNoProof();
//		run.addNewT().setStringValue(chapterNum + "  " + title);
		run.addNewT().setStringValue(title);
		run = hyperlink.addNewR();
		CTRPr rPr = run.addNewRPr();
		rPr.addNewNoProof();
		rPr.addNewWebHidden();
		run.addNewTab();
		run = hyperlink.addNewR();
		rPr = run.addNewRPr();
		rPr.addNewNoProof();
		rPr.addNewWebHidden();
		run.addNewFldChar().setFldCharType(STFldCharType.BEGIN);
		// pageref run
		run = hyperlink.addNewR();
		rPr = run.addNewRPr();
		rPr.addNewNoProof();
		rPr.addNewWebHidden();
		CTText text = run.addNewInstrText();
		text.setSpace(SpaceAttribute.Space.PRESERVE);
		// bookmark reference
		text.setStringValue(" PAGEREF " + bookmarkRef + " \\h ");
		run = hyperlink.addNewR();
		rPr = run.addNewRPr();
		rPr.addNewNoProof();
		rPr.addNewWebHidden();
		run = hyperlink.addNewR();
		rPr = run.addNewRPr();
		rPr.addNewNoProof();
		rPr.addNewWebHidden();
		run.addNewFldChar().setFldCharType(STFldCharType.SEPARATE);
		// page number run
		run = hyperlink.addNewR();
		rPr = run.addNewRPr();
		rPr.addNewNoProof();
		rPr.addNewWebHidden();
		run.addNewT().setStringValue(Integer.toString(page));
		run = hyperlink.addNewR();
		rPr = run.addNewRPr();
		rPr.addNewNoProof();
		rPr.addNewWebHidden();
		run.addNewFldChar().setFldCharType(STFldCharType.END);
	}

	private static int pageBreak(XWPFParagraph currentPar, String headTitle, List<IBodyElement> bodyElementList) {
		int pageBreak = 2;
		boolean findFlag = false;
		if (bodyElementList != null && bodyElementList.size() > 0) {
			for (int i = 0; i < bodyElementList.size() && !findFlag; i++) {
				// 分页符的位置可能出现在段落，或者表格下
				IBodyElement element = bodyElementList.get(i);
				if (element instanceof XWPFParagraph) {
					XWPFParagraph par = (XWPFParagraph) element;

					String title = par.getText();
					List<CTR> ctrlist = par.getCTP().getRList();//获取<w:p>标签下的<w:r>list
					for (int j = 0; j < ctrlist.size(); j++) {  //遍历r
						CTR r = ctrlist.get(j);
//						List<CTEmpty> breaklist = r.getLastRenderedPageBreakList();//判断是否存在此标签
//						if (breaklist.size() > 0) {
//							pageBreak++; //页数添加
//						}
						if (par.isPageBreak()) {
							pageBreak++;
						}
						if (headTitle.equals(title) && currentPar == par) {
							findFlag = true;
							break;
						}
					}
				} else if (element instanceof XWPFTable) {
					XWPFTable table = (XWPFTable) element;
					List<XWPFTableRow> tableList = table.getRows();
					if (tableList != null && tableList.size() > 0) {
						// 表格中最新一次数据所在的行号
						int lastBreakRow = 0;
						// 当前数据行号
						int rowindex = -1;
						for (XWPFTableRow row : tableList) {
							rowindex++;
							// 同一表格下，可能同一行有多个lastRenderedPageBreak
							boolean findBreakFlag = false;
							List<XWPFTableCell> cellList = row.getTableCells();
							if (cellList != null && cellList.size() > 0) {
								for (int m = 0; m < cellList.size() && !findBreakFlag; m++) {
									XWPFTableCell cell = cellList.get(m);
									List<XWPFParagraph> paragraphList = cell.getParagraphs();
									if (paragraphList != null && paragraphList.size() > 0) {
										for (int n = 0; n < paragraphList.size() && !findBreakFlag; n++) {
											XWPFParagraph par = paragraphList.get(n);
											List<CTR> ctrlist = par.getCTP().getRList();//获取<w:p>标签下的<w:r>list
											for (int j = 0; j < ctrlist.size() && !findBreakFlag; j++) {  //遍历r
												CTR r = ctrlist.get(j);
												List<CTEmpty> breaklist = r.getLastRenderedPageBreakList();//判断是否存在此标签
												if (breaklist.size() > 0) {
													findBreakFlag = true;
													// 处理分隔符在表格前后两行都存在的问题
													if (lastBreakRow == 0 || rowindex - lastBreakRow > 1) {
														lastBreakRow = rowindex;
														pageBreak++; //页数添加
														break;
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}

			}
		}
		if (!findFlag) {
			pageBreak = 1;
		}
		return pageBreak;
	}
}
