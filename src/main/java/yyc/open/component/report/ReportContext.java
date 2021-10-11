package yyc.open.component.report;

import lombok.Builder;
import lombok.Getter;
import yyc.open.component.report.ReportConfig.GlobalConfig;

/**
 * {@link ReportContext}
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/7/28
 */
@Getter
@Builder
public class ReportContext {

	private GlobalConfig globalConfig;
	private ReportStatus reportStatus;
}
