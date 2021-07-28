package yyc.open.framework.microants.components.kit.report;

/**
 * {@link ReportContextFactory}
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/7/28
 */
public class ReportContextFactory {
    private ThreadLocal<ReportContext> context;

    public void setContext(final ReportContext reportContext) {
        context = ThreadLocal.withInitial(() -> reportContext);
    }

    public ReportContext getContext() {
        return context.get();
    }

    public void close() {
        this.context.remove();
    }

    enum ReportContextFactoryEnum {
        INSTANCE;

        private ReportContextFactory reportContextFactory;

        public ReportContextFactory getReportContextFactory() {
            if (null == reportContextFactory) {
                reportContextFactory = new ReportContextFactory();
            }
            return reportContextFactory;
        }
    }
}
