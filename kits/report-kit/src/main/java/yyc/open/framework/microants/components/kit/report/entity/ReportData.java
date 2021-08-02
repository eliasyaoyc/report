package yyc.open.framework.microants.components.kit.report.entity;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

/**
 * {@link ReportData}
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/8/1
 */
@Getter
@Builder
public class ReportData {
    /**
     * Chart title.
     */
    private String title;

    /**
     * The legend name that only to Bar and Pie.
     */
    private String legendName;


    private List<Object[]> xdatas;

    private Object[] yDatas;
}
