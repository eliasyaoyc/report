package yyc.open.framework.microants.components.kit.report;

import com.google.common.collect.Maps;
import sun.misc.BASE64Encoder;
import yyc.open.framework.microants.components.kit.common.uuid.UUIDsKit;
import yyc.open.framework.microants.components.kit.common.validate.NonNull;
import yyc.open.framework.microants.components.kit.report.commons.ReportEnums;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.zip.CRC32;

/**
 * {@link ReportTaskRegistry}
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/7/30
 */
public class ReportTaskRegistry {
    private ReportStatus reportStatus;
    private Map<String, ReportTask> tasks;
    private Map<String, ReportTask> failTasks;
    private Map<Long, String /*checksum, image file*/> paths;
    private Map<Long, String /*checksum, image base64*/> base64Maps;

    private ReportTaskRegistry(ReportStatus reportStatus) {
        this.reportStatus = reportStatus;
        this.tasks = Maps.newConcurrentMap();
        this.failTasks = Maps.newConcurrentMap();
        this.paths = Maps.newConcurrentMap();
        this.base64Maps = Maps.newConcurrentMap();
    }

    public void updateChecksum(String taskId, String path) {
        // Notice：the partially completed event only scoped to echarts generation.
        ReportTask task = this.tasks.get(taskId);
        Long checksum = checksum(task);
        this.paths.putIfAbsent(checksum, path);
        this.base64Maps.putIfAbsent(checksum, imageToBase64ByLocal(path));
    }

    public enum ReportRegistryEnum {
        INSTANCE;

        ReportTaskRegistry reportRegistry;

        public ReportTaskRegistry getReportRegistry(ReportStatus reportStatus) {
            if (reportRegistry == null) {
                reportRegistry = new ReportTaskRegistry(reportStatus);
            }
            return reportRegistry;
        }
    }

    /**
     * Returns the task collections to support parallel execution.
     *
     * @param config need to generate report.
     * @param entity data need to generate report.
     * @return
     */
    public List<ReportTask> createTask(ReportConfig config, ReportMetadata entity) {
        List<ReportTask> tasks = new ArrayList<>();
        entity.getContent().getData().stream().forEach(data -> {
            data.stream().forEach(item -> {
                if (ReportEnums.isCharts(item.getType())) {
                    String taskId = UUIDsKit.base64UUID();
                    ReportTask task = ReportTask.builder()
                            .reportId(entity.getReportId())
                            .reportName(entity.getReportName())
                            .outputPath(config.getOutputPath())
                            .templatePath(item.getTemplateUrl())
                            .taskId(taskId)
                            .data(ReportTask.parseReportData(item))
                            .build();
                    task.setReportType(item.getType());

                    item.setTaskId(taskId);

                    // Calculate task checksum.
                    Long checksum = checksum(task);
                    if (this.paths.containsKey(checksum)) { // Must be exists.
                        String value = ""; // base64 if report type is pdf or html, otherwise url.
                        if (entity.getReportType() == ReportEnums.WORD) {
                            // Find the duplicate task so used result directly.
                            value = this.paths.get(checksum);
                        } else if (entity.getReportType() == ReportEnums.HTML || entity.getReportType() == ReportEnums.PDF) {
                            value = this.base64Maps.get(checksum);
                        }
                        entity.setSubTaskExecutionResult(taskId, value);
                    } else {
                        tasks.add(task);
                        this.tasks.put(taskId, task);
                    }
                }
            });
        });
        this.reportStatus.publishEvent(entity.getReportId(), ReportEvent.EventType.CREATION);
        return tasks;
    }

    private static Long checksum(ReportTask task) {
        CRC32 crc32 = new CRC32();
        crc32.update(task.getData().getBytes(StandardCharsets.UTF_8), 0, task.getData().length());
        return crc32.getValue();
    }

    /**
     * Add the task that
     *
     * @param taskId
     */
    public void addToFailQueue(@NonNull String taskId) {
        if (!this.tasks.containsKey(taskId)) {
            return;
        }

        ReportTask failTask = this.tasks.remove(taskId);
        if (!Objects.isNull(failTask)) {
            this.failTasks.put(taskId, failTask);
        }
    }

    /**
     * Generation base64 through input file path.
     *
     * @param imgFile input file path.
     * @return base64.
     */
    static String imageToBase64ByLocal(String imgFile) {
        if (!new File(imgFile).exists()) {
            return null;
        }
        InputStream in = null;
        byte[] data = null;

        try {
            in = new FileInputStream(imgFile);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        BASE64Encoder encoder = new BASE64Encoder();
        return "data:img/jpg;base64," + encoder.encode(data);
    }
}
