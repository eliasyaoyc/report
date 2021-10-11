package yyc.open.component.report.listener;


import static yyc.open.component.report.commons.ReportConstants.DEFAULT_LISTENER;
import static yyc.open.component.report.commons.ReportConstants.LISTENER;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import yyc.open.component.report.ReportEvent;
import yyc.open.component.report.ReportMetadata;
import yyc.open.component.report.commons.Processor;

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
