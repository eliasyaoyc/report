package yyc.open.component.report;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 * {@link ReportConfig}
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/7/28
 */
@Getter
@Setter
public class ReportConfig {

	private List<String> colors;
	private Boolean sw = false;
	private String watermark;
	private Boolean horizontal;
	private Integer port;
	private Boolean parallel;
	AlarmConfig alarm;
	private Integer maxTaskNum;
	private String outputPath;
	private String jsPath;
	private String execPath;
	private String templatesPath;
	private String jsPdfPath;
	private Boolean notUseTemp;

	@Getter
	@Setter
	public static class GlobalConfig {

		Integer port;
		Boolean sw;
		String watermark;
		Boolean horizontal;
		Boolean parallel;
		Boolean chart;
		AlarmConfig alarm;
		List<String> colors;
		Integer maxTaskNum;
		String outputPath;
		String jsPath;
		String execPath;
		String templatesPath;
		String jsPdfPath;
	}

	@Getter
	@Setter
	public static class AlarmConfig {

		boolean enabled;
		String type;
		String address;
	}
}
