package yyc.open.framework.microants.components.kit.audit;

/**
 * @author Elias (siran0611@gmail.com)
 */
public class AuditBuilder {

	private AuditOptions options;

	private AuditBuilder() {
		this.options = AuditOptions.createDefault();
	}

	public static AuditBuilder newInstance() {
		return new AuditBuilder();
	}


	public AuditBuilder threadNum(int num) {
		this.options.setScatterNum(Math.min(num, Runtime.getRuntime().availableProcessors() * 2));
		return this;
	}


	public Audit build() {
		return new Audit(this.options);
	}
}
