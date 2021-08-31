package yyc.open.framework.microants.components.kit.audit;

import java.util.List;

/**
 * @author Elias (siran0611@gmail.com)
 */
public class AuditFactory {

	public static final AuditFactory INSTANCE = new AuditFactory();
	private List<AuditInterceptor> interceptors;
	private List<AuditStorage> storages;

	/**
	 * Lazy load.
	 */
	private AuditFactory() {
		this.interceptors = AuditServiceLoader.load(AuditInterceptor.class).sort();
		this.storages = AuditServiceLoader.load(AuditStorage.class).sort();
	}

	public List<AuditInterceptor> getInterceptors() {
		return this.interceptors;
	}

	public List<AuditStorage> getStorages() {
		return this.storages;
	}
}
