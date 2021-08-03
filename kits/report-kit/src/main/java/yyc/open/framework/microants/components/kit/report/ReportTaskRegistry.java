package yyc.open.framework.microants.components.kit.report;

import com.google.common.collect.Maps;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.reflect.TypeLiteral;
import yyc.open.framework.microants.components.kit.common.uuid.UUIDsKit;
import yyc.open.framework.microants.components.kit.common.validate.NonNull;
import yyc.open.framework.microants.components.kit.report.entity.ReportData;
import yyc.open.framework.microants.components.kit.report.entity.ReportEntity;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * {@link ReportTaskRegistry}
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/7/30
 */
public class ReportTaskRegistry {
    private ReportStatus reportStatus;
    private Map<String, ReportTask> tasks = Maps.newConcurrentMap();
    private Map<String, ReportTask> failTasks = Maps.newConcurrentMap();
    private final List<MatcherAndConverter> converters = new ArrayList<>();

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

    private ReportTaskRegistry(ReportStatus reportStatus) {
        this.reportStatus = reportStatus;
        initConverters();
    }

    void initConverters() {
        convertToEChart(ReportData.class, ReportTask.class);
        convertToEChart(ReportData.class, ReportTask.class);
        convertToEChart(ReportData.class, ReportTask.class);
        convertToEChart(ReportData.class, ReportTask.class);
        convertToEChart(ReportData.class, ReportTask.class);
        convertToEChart(ReportData.class, ReportTask.class);
    }

    /**
     * Returns the task collections to support parallel execution.
     *
     * @param config   need to generate report.
     * @param entities data need to generate report.
     * @return
     */
    public List<ReportTask> createTask(ReportConfig config, List<ReportEntity> entities) {
        List<ReportTask> tasks = new ArrayList<>();

        String taskId = UUIDsKit.base64UUID();
        entities.stream().forEach(entity -> {
            entity.getContent().getData().stream().forEach(data -> {
                data.stream().forEach(item -> {
                    if (CollectionUtils.isEmpty(item.getTexts())
                            && CollectionUtils.isEmpty(item.getTables())) {

                        ReportTask task = ReportTask.builder()
                                .reportId(entity.getReportId())
                                .reportName(entity.getReportName())
                                .outputPath(config.getOutputPath())
                                .taskId(taskId)
                                .type(item.getType())
                                .build();
                        tasks.add(task);
                    }
                });
            });
        });

        this.reportStatus.publishEvent(taskId, "", ReportEvent.EventType.CREATION);
        return null;
    }

    /**
     * Add the task that
     *
     * @param taskId
     */
    public void addToFailQueue(@NonNull String taskId) {
        ReportTask failTask = this.tasks.remove(taskId);
        this.failTasks.put(taskId, failTask);
    }

    public static String capitalize(String s) {
        if (s.length() == 0) {
            return s;
        }
        char first = s.charAt(0);
        char capitalized = Character.toUpperCase(first);
        return (first == capitalized)
                ? s
                : capitalized + s.substring(1);
    }

    private <S, D> void convertToEChart(Class<S> source, final Class<D> wrapperType) {
        try {
            final Method parser = wrapperType.getMethod(
                    "parseReportData", ReportData.class);
            Converter converter = new Converter() {
                @Override
                public Object convert(Object value, TypeLiteral<?> toType) {
                    try {
                        return parser.invoke(null, value);
                    } catch (IllegalAccessException e) {
                        throw new AssertionError(e);
                    } catch (InvocationTargetException e) {
                        throw new RuntimeException(e.getTargetException());
                    }
                }
            };
            convertToClass(wrapperType, converter);
        } catch (NoSuchMethodException e) {
            throw new AssertionError(e);
        }
    }

    private <T> void convertToClass(Class<T> type, Converter converter) {
        convertToClasses(Matchers.identicalTo(type), converter);
    }

    private void convertToClasses(final Matcher<? super Class<?>> matcher, Converter converter) {
        internalConvertToTypes(new AbstractMatcher<TypeLiteral<?>>() {
            @Override
            public boolean matches(TypeLiteral<?> typeLiteral) {
                Type type = typeLiteral.getType();
                if (!(type instanceof Class)) {
                    return false;
                }
                Class<?> clazz = (Class<?>) type;
                return matcher.matches(clazz);
            }
        }, converter);
    }

    private void internalConvertToTypes(Matcher<? super TypeLiteral<?>> matcher,
                                        Converter converter) {
        this.converters.add(new MatcherAndConverter(matcher, converter));
    }
}
