package yyc.open.framework.microants.components.kit.report;

import yyc.open.framework.microants.components.kit.report.commons.ReportEnums;

import java.util.List;

/**
 * {@link ReportConfig}
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/7/28
 */
public class ReportConfig<T> {
    private List<String> colors;
    private ReportEnums type;
    private boolean watermark;
    private boolean horizontal;
    private T data;

    public List<String> getColors() {
        return colors;
    }

    public void setColors(List<String> colors) {
        this.colors = colors;
    }

    public ReportEnums getType() {
        return type;
    }

    public void setType(String type) {
        this.type = ReportEnums.getType(type);
    }

    public boolean isWatermark() {
        return watermark;
    }

    public void setWatermark(boolean watermark) {
        this.watermark = watermark;
    }

    public boolean isHorizontal() {
        return horizontal;
    }

    public void setHorizontal(boolean horizontal) {
        this.horizontal = horizontal;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
