package yyc.open.component.report;

import lombok.Getter;
import lombok.Setter;
import yyc.open.component.report.commons.ReportEnums;

/**
 * {@link Task}
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/8/3
 */
@Getter
@Setter
public class Task {

	/**
	 * The type of report. e.g. pdf, word, html etc.
	 */
	private ReportEnums reportType;

}
