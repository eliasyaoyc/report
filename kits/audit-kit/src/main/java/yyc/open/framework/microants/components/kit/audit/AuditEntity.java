package yyc.open.framework.microants.components.kit.audit;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Elias (siran0611@gmail.com)
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuditEntity {

	private String userName;
	private String userRole;
	private String ip;
	private String action;
	private String content;
	private Boolean isSuccess;
	private Long createTime;
	private String module;

	public static AuditEntity success(
			String userName,
			String userRole,
			String ip,
			String action,
			String content,
			Long createTime,
			String module) {
		return new AuditEntity(userName, userRole, ip, action, content, true, createTime, module);
	}

	public static AuditEntity failure(
			String userName,
			String userRole,
			String ip,
			String action,
			String content,
			Long createTime,
			String module) {
		return new AuditEntity(userName, userRole, ip, action, content, false, createTime, module);
	}
}
