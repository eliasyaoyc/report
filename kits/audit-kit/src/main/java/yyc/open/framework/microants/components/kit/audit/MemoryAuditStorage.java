package yyc.open.framework.microants.components.kit.audit;

import java.util.List;
import yyc.open.framework.microants.components.kit.common.annotations.VisibleForTesting;

/**
 * @author Elias (siran0611@gmail.com)
 */
@VisibleForTesting
@AuditSPI
public class MemoryAuditStorage implements AuditStorage {

	@Override
	public boolean insert(Object entity) {
		System.out.println("1111111111");
		return false;
	}

	@Override
	public boolean batchInsert(List entity) {
		return false;
	}
}
