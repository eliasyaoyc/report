package yyc.open.framework.microants.components.kit.audit;

import lombok.AllArgsConstructor;
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
		audit.publishEvent(new TestEntity("yyc", 12));
	}


	@Getter
	@Setter
	@AllArgsConstructor
	class TestEntity extends AuditEntity {

		private String name;
		private Integer age;
	}
}
