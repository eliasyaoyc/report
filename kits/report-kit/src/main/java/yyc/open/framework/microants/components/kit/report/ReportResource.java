package yyc.open.framework.microants.components.kit.report;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

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
    private List<Long> checksums;
    private String reportRootPath;
}
