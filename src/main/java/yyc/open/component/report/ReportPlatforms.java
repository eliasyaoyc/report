package yyc.open.component.report;

import java.io.IOException;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import yyc.open.component.report.commons.file.FileKit;
import yyc.open.component.report.exceptions.ReportException;

/**
 * {@link ReportPlatforms}
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/8/6
 */
@Getter
public enum ReportPlatforms {
	WINDOWS("Windows", "phantomjs-windows.exe"),

	MACOS("Mac OS X", "phantomjs-macosx"),

	UNIX("Linux", "phantomjs-linux");

	private static final Logger LOGGER = LoggerFactory.getLogger(ReportPlatforms.class);

	String name;
	String path;


	ReportPlatforms(String name, String path) {
		this.name = name;
		this.path = path;
	}

	/**
	 * Returns the corresponding platform through input parma.
	 *
	 * @param name
	 * @return
	 */
	public static ReportPlatforms getPlatforms(String name) {
		for (ReportPlatforms pf : ReportPlatforms.values()) {
			if (name.startsWith(pf.getName())) {
				return pf;
			}
		}
		String message = String.format("[Report Runtime] current system : %s is not support.", name);
		throw new IllegalArgumentException(message);
	}

	public static void runPhantomStartCommand(ReportConfig.GlobalConfig config) {
		String command = "";
		try {
			String property = System.getProperty("os.name");
			ReportPlatforms platforms = ReportPlatforms.getPlatforms(property);
			String path = FileKit.tempFile(config.getExecPath() + platforms.getPath());

			// Notice: we need to generation temp file /lib/jquery-3.2.1.min.js, /lib/echarts.min.js,/lib/china.js.
			String newPath = FileKit.createDir(FileKit.tempPath() + "lib");
			FileKit.tempFile("js/lib/jquery-3.2.1.min.js", newPath);
			FileKit.tempFile("js/lib/echarts.min.js", newPath);
			FileKit.tempFile("js/lib/china.js", newPath);

			command = new StringBuilder(path)
					.append(" ")
					.append(FileKit.tempFile(config.getJsPath()))
					.append(" -s -p ")
					.append(config.getPort()).toString();

			LOGGER.info("[ReportPlatforms] command: {}", command);

			if (!property.contains(WINDOWS.getName())) {
				Runtime.getRuntime().exec("chmod 777 " + path);
			}
			Runtime.getRuntime().exec(command);
		} catch (IOException e) {
			throw new ReportException(String.format("[Report Runtime] exec command: %s fail", command), e);
		}
	}

	public static void pdfConvertCommand(String htmlPath, String pdfName, String outputPath, String execPath, String jsPdfPath) {
		String command = "";
		String property = System.getProperty("os.name");
		ReportPlatforms platforms = ReportPlatforms.getPlatforms(property);

		try {
			String path = FileKit.tempFile(execPath + platforms.getPath());

			command = new StringBuilder(path)
					.append(" ")
					.append(FileKit.tempFile(jsPdfPath))
					.append(" " + htmlPath)
					.append(" " + pdfName)
					.append(" " + outputPath)
					.toString();

			if (!property.contains(WINDOWS.getName())) {
				Runtime.getRuntime().exec("chmod 777 " + path);
			}
			LOGGER.info("[PDF generation] generate pdf command : {}", command);
			Process p = Runtime.getRuntime().exec(command);
			p.waitFor();
			p.destroy();
		} catch (Exception e) {
			throw new ReportException(String.format("[Report Runtime] exec command: %s fail", command), e);
		}
	}
}
