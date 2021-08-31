package yyc.open.framework.microants.components.kit.audit.util;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Elias (siran0611@gmail.com)
 */
public class NamedThreadFactory implements ThreadFactory {

	private static final Logger LOG = LoggerFactory.getLogger(NamedThreadFactory.class);

	private final String prefix;
	private final boolean daemon;
	private AtomicLong counter = new AtomicLong(0);
	private final UncaughtExceptionHandler handler = new UncaughtExceptionHandler() {
		@Override
		public void uncaughtException(Thread t, Throwable e) {
			LOG.error("Thread: {} occurred exception : {}", t.getName(), e);
		}
	};

	public NamedThreadFactory(String prefix, boolean daemon) {
		this.prefix = prefix;
		this.daemon = daemon;
	}

	@Override
	public Thread newThread(Runnable r) {
		Thread t = new Thread(r);
		t.setName(prefix + "-" + counter.incrementAndGet());
		t.setDaemon(daemon);
		t.setUncaughtExceptionHandler(handler);
		t.setPriority(Thread.NORM_PRIORITY);
		return t;
	}
}
