package yyc.open.framework.microants.components.kit.report;

import lombok.Builder;
import lombok.Getter;
import yyc.open.framework.microants.components.kit.report.commons.ReportEnums;

import java.util.List;

/**
 * {@link ReportTask}
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/7/30
 */
@Builder
@Getter
public class ReportTask {
    private String reportId;
    private String reportName;
    private String outputPath;
    private List<ReportTaskChild> childs;

    @Builder
    @Getter
    public static class ReportTaskChild {
        String taskId;
        ReportEnums type;
        String data;
        Integer page;
        Integer priority;
    }

    /**
     * Returns the task collections to support parallel execution.
     *
     * @param configs
     * @return
     */
    public static List<ReportTask> createTask(List<ReportConfig> configs) {

        ReportEvent event = ReportEvent.builder()
                .type(ReportEvent.EventType.CREATION)
                .build();
        ReportContext.listenerFactory().onEvent(event);
        return null;
    }
}
