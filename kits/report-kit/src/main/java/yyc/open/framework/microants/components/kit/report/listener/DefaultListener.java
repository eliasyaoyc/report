package yyc.open.framework.microants.components.kit.report.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import yyc.open.framework.microants.components.kit.report.ReportEvent;
import yyc.open.framework.microants.components.kit.report.commons.Processor;
import yyc.open.framework.microants.components.kit.report.ReportMetadata;

import static yyc.open.framework.microants.components.kit.report.commons.ReportConstants.DEFAULT_LISTENER;
import static yyc.open.framework.microants.components.kit.report.commons.ReportConstants.LISTENER;

/**
 * {@link DefaultListener}
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/7/31
 */
@Processor(name = DEFAULT_LISTENER, type = LISTENER)
public class DefaultListener implements Listener {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultListener.class);

    @Override
    public void onSuccess(ReportEvent event) {
        switch (event.getType()) {
            case REPORT:
                ReportMetadata reportMetadata = event.getEntity();
                // Send the report task.
            case COMPLETED:
                LOGGER.info("");
        }
    }

    @Override
    public void onFailure(ReportEvent event) {
    }
}
