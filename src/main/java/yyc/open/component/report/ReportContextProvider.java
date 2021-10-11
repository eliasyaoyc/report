package yyc.open.component.report;

/**
 * {@link ReportContextProvider}
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/7/28
 */
public class ReportContextProvider {

	public static final ReportContextProvider INSTANCE = new ReportContextProvider();

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
}
