package yyc.open.framework.microants.components.kit.audit;

import lombok.Getter;
import lombok.Setter;
import org.junit.Test;

/**
 * @author Elias (siran0611@gmail.com)
 */
public class AuditTest {

	private Audit audit;

	{
		audit = AuditBuilder.newInstance()
				.disruptorBufferSize(1024)
				.build();
	}

	@Test
	public void testAudit() {
		audit.publishEvent(new TestEntity("yyc", "sys", "1.1.1.1", "添加", "添加用户", true, 121212121L, ""));
	}


	@Getter
	@Setter
	class TestEntity extends AuditEntity {

		private String name;
		private Integer age;

		public TestEntity(String userName, String userRole, String ip, String action, String content, Boolean isSuccess,
				Long createTime, String module) {
			super(userName, userRole, ip, action, content, isSuccess, createTime, module);
		}
	}
}
