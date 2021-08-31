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

	public AuditBuilder disruptorBufferSize(int size) {
		options.setDisruptorBufferSize(size);
		return this;
	}

	public Audit build() {
		return new Audit(options);
	}
}
