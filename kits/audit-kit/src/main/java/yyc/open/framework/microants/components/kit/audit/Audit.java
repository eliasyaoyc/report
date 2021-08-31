package yyc.open.framework.microants.components.kit.audit;

import com.google.common.collect.Lists;
import java.util.List;
import javax.annotation.Nonnull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Elias (siran0611@gmail.com)
 */
public class Audit {

	private static final Logger LOG = LoggerFactory.getLogger(Audit.class);

	private List<Scatter> scatters;

	protected Audit(@Nonnull final AuditOptions options) {
		// Constructor scatter.
		this.scatters = Lists.newArrayListWithCapacity(options.getScatterNum());
		for (int _i = 0; _i < options.getScatterNum(); _i++) {
			this.scatters.add(new Scatter());
		}
	}


	private final static class Scatter implements Runnable {

		private Scatter() {

		}

		@Override
		public void run() {

		}
	}
}
