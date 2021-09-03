package yyc.open.framework.microants.components.kit.audit;

import java.time.LocalDateTime;
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

	private String action;
	private String content;
	private Boolean result;
	private String module;
	private LocalDateTime createTime;
}
