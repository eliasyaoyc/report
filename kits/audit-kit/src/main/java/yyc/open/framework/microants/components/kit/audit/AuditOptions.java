package yyc.open.framework.microants.components.kit.audit;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Elias (siran0611@gmail.com)
 */
@Getter
@Setter
public class AuditOptions {

	/**
	 *
	 */
	private int scatterNum;


	public static AuditOptions createDefault() {
		return new AuditOptions();
	}
}
