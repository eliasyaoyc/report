package yyc.open.framework.microants.components.kit.audit;

/**
 * @author Elias (siran0611@gmail.com)
 */
public class TracingBuilder {

	private TracingOptions options;

	private TracingBuilder() {
		this.options = TracingOptions.createDefault();
	}

	public static TracingBuilder newInstance() {
		return new TracingBuilder();
	}


	public TracingBuilder threadNum(int num) {
		this.options.setScatterNum(Math.min(num, Runtime.getRuntime().availableProcessors() * 2));
		return this;
	}


	public Tracing build() {
		return new Tracing(this.options);
	}
}
