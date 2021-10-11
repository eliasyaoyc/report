package yyc.open.component.report;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * {@link ReportResource}
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/8/18
 */
@Builder
@Getter
@Setter
public class ReportResource {

	private String reportId;
	private List<String> taskId;
	private List<String> checksums;
	private String reportRootPath;
}
