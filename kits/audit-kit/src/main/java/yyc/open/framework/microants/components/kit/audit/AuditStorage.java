package yyc.open.framework.microants.components.kit.audit;

import java.util.List;

/**
 * @author Elias (siran0611@gmail.com)
 */
public interface AuditStorage<T> {

	boolean insert(T entity);

	boolean batchInsert(List<T> entity);
}
