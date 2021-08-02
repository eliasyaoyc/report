package yyc.open.framework.microants.components.kit.report.handler;

import com.google.gson.GsonBuilder;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import yyc.open.framework.microants.components.kit.common.file.FileKit;
import yyc.open.framework.microants.components.kit.http.HttpKit;
import yyc.open.framework.microants.components.kit.http.Result;
import yyc.open.framework.microants.components.kit.report.ReportConfig;
import yyc.open.framework.microants.components.kit.report.ReportContext;
import yyc.open.framework.microants.components.kit.report.ReportContextFactory;
import yyc.open.framework.microants.components.kit.report.ReportTask;
import yyc.open.framework.microants.components.kit.report.commons.Processor;
import yyc.open.framework.microants.components.kit.report.entity.PhantomJS;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

import static yyc.open.framework.microants.components.kit.report.commons.ReportConstants.CHART_HANDLE;
import static yyc.open.framework.microants.components.kit.report.commons.ReportConstants.HANDLER;

/**
 * {@link ChartHandler}
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/7/28
 */
@Processor(name = CHART_HANDLE, type = HANDLER)
public class ChartHandler<T> extends AbstractHandler<T> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ChartHandler.class);
    private ReportConfig.GlobalConfig globalConfig;
    private final String templatePath;

    {
        templatePath = ChartHandler.class.getClassLoader().getResource("").getPath() + "templates";
        globalConfig = ReportContextFactory.ReportContextFactoryEnum.INSTANCE
                .getReportContextFactory()
                .getContext()
                .getGlobalConfig();
    }

    @Override
    public void onHandle(T task) {
        ReportTask.ReportTaskChild t = (ReportTask.ReportTaskChild) task;
        LOGGER.info("[Chard Handler] handle the task {}", t.getTaskId());

        try {
            String option = generateFreemarkerTemplate(t.getType().getTemplateName(), new HashMap<>());
            Map<String, Object> opt = new GsonBuilder().create().fromJson(option, Map.class);

            PhantomJS req = PhantomJS.builder()
                    .opt(opt)
                    .reqMethod("echarts")
                    .file(t.getPath())
                    .build();

            Result result = HttpKit.builder()
                    .post(String.format("localhost:{}", globalConfig.getPort()))
                    .body(new GsonBuilder().create().toJson(req))
                    .build().get();

            // Determine whether to generate a watermark.
            if (StringUtils.isNotEmpty(globalConfig.getWatermark())) {
                generateWatermark(globalConfig.getWatermark(), t.getPath());
            }

            super.reportCompleteness(t.getTaskId());
        } catch (Exception e) {
            LOGGER.error("[Chart Handler] generate chart encountered error: {}", e);
            // add to fail queue.
            ReportContext.reportRegistry().addToFailQueue(t.getTaskId());
        }
    }

    /**
     * Generate the freemarker template.
     *
     * @param templateName
     * @param data
     * @return
     * @throws IOException
     * @throws TemplateException
     */
    private String generateFreemarkerTemplate(String templateName, Map<String, Object> data) throws IOException, TemplateException {
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_0);
        configuration.setDefaultEncoding("UTF-8");
        configuration.setDirectoryForTemplateLoading(new File(templatePath));
        Template template = configuration.getTemplate(templateName);
        try (StringWriter sw = new StringWriter()) {
            template.process(data, sw);
            sw.flush();
            return sw.getBuffer().toString();
        }
    }

    /**
     * Add watermark.
     *
     * @param watermark
     * @param path
     */
    private void generateWatermark(String watermark, String path) {
        InputStream is = null;
        OutputStream os = null;

        try {
            // 1. File source.
            Image srcImg = ImageIO.read(new File(path));
            LOGGER.info("srcImg = " + srcImg);

            BufferedImage buffImg = new BufferedImage(srcImg.getWidth(null), srcImg.getHeight(null), BufferedImage.TYPE_INT_RGB);
            // 2. Get the brush object.
            Graphics2D g = buffImg.createGraphics();
            // 3. Sets the jagged edge treatment for a line segment.
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g.drawImage(srcImg.getScaledInstance(srcImg.getWidth(null), srcImg.getHeight(null), Image.SCALE_SMOOTH), 0, 0, null);
            // 4. Sets the watermark rotation.
            g.rotate(Math.toRadians(-15), buffImg.getWidth() / 2, buffImg.getHeight() / 2);
            // 5. Sets the watermark color.
            g.setColor(new Color(190, 190, 190));
            // 6. Sets the watermark front.
            g.setFont(new Font("宋体", Font.BOLD, buffImg.getHeight() / 6));
            // 7. Sets the watermark text transparency.
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 0.15f));
            // 8. The first parameter -> set the content, the last two parameters -> the coordinate position of the text on the image (x,y)
            g.drawString(watermark, buffImg.getWidth() / 4, buffImg.getHeight() / 2);
            // 9、Release resource.
            g.dispose();
            // 10. Generate.
            os = new FileOutputStream(path);
            ImageIO.write(buffImg, FileKit.suffix(path, "."), os);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != is)
                    is.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (null != os)
                    os.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
