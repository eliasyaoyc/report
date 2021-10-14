package yyc.open.component.report.handler;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.util.Map;
import javax.imageio.ImageIO;
import yyc.open.component.report.commons.file.FileKit;
import yyc.open.component.report.commons.validate.Asserts;

/**
 * {@link AbstractHandler}
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/7/31
 */
public abstract class AbstractHandler<T> implements Handler<T> {

	/**
	 * Generate the freemarker template.
	 *
	 * @param templatePath
	 * @param data
	 * @return
	 * @throws IOException
	 * @throws TemplateException
	 */
	String generateFreemarkerTemplate(String templatePath, Map<String, Object> data) throws
			IOException, TemplateException {
		Asserts.notEmpty(data, "data is empty.");

		String[] path = FileKit.splitWithSuffix(FileKit.tempFile(templatePath,true), File.separator);
		Asserts.isTrue(path.length == 2);

		Configuration configuration = new Configuration(Configuration.VERSION_2_3_0);
		configuration.setDefaultEncoding("UTF-8");
		configuration.setDirectoryForTemplateLoading(new File(path[0]));
		Template template = configuration.getTemplate(path[1]);
		try (StringWriter sw = new StringWriter()) {
			template.process(data, sw);
			sw.flush();
			return sw.getBuffer().toString();
		}
	}

	String generateFreemarkerTemplateByDefault(String path, String templateName, Map<String, Object> data) throws
			IOException, TemplateException {
		return generateFreemarkerTemplate(path + templateName, data);
	}

	/**
	 * Add watermark.
	 *
	 * @param watermark
	 * @param path
	 */
	void generateWatermark(String watermark, String path) {
		InputStream is = null;
		OutputStream os = null;

		try {
			// 1. File source.
			Image srcImg = ImageIO.read(new File(path));

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
			ImageIO.write(buffImg, FileKit.suffix(path, '.'), os);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != is) {
					is.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				if (null != os) {
					os.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
