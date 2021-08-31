package yyc.open.framework.microants.components.kit.audit;

/**
 * @author Elias (siran0611@gmail.com)
 */
public @interface AuditSPI {

	String name() default "";

	int priority() default 0;
}
