package yyc.open.component.report;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * {@link ReportThreadPool}
 *
 * @author <a href="mailto:siran0611@gmail.com">siran.yao</a>
 * @version ${project.version}
 * @date 2021/8/2
 */
public class ReportThreadPool extends ThreadPoolExecutor {

	private static int core = Runtime.getRuntime().availableProcessors();

	public ReportThreadPool(String threadName) {
		super(core, core * 2, 0, TimeUnit.SECONDS, new ArrayBlockingQueue<>(1024), new ThreadFactory() {
			AtomicInteger index = new AtomicInteger();

			@Override
			public Thread newThread(Runnable r) {
				Thread t = new Thread(r);
				t.setName(threadName + index.getAndIncrement());
				t.setPriority(Thread.NORM_PRIORITY);
				t.setDaemon(false);
				return t;
			}
		});
	}
}
