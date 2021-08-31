package yyc.open.framework.microants.components.kit.audit;

/**
 * Interceptor is a superclass, so user side can implement it.
 * {@link AuditFactory#loadServices()} will load all class annotated by {@link AuditSPI} and assemble
 * a interceptor chain so executed it when has audit entry received.
 *
 * @author Elias (siran0611@gmail.com)
 */
@FunctionalInterface
public interface AuditInterceptor<T> {

	void interceptor(T entity);
}
