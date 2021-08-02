package yyc.open.framework.microants.components.kit.report.handler;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import yyc.open.framework.microants.components.kit.report.ReportCallback;
import yyc.open.framework.microants.components.kit.report.commons.Processor;

import static yyc.open.framework.microants.components.kit.report.commons.ReportConstants.*;

/**
 * {@link FileHandler}
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/7/28
 */
@Processor(name = FILE_HANDLE, type = HANDLER)
public class FileHandler<T> extends AbstractHandler<T> {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileHandler.class);

    @Override
    public void onHandle(T task, ReportCallback callback) {
        // Generate html.
        // Determine which file convert to.
    }

    void convertToPdf() {

    }

    void convertToWord() {

    }
}
