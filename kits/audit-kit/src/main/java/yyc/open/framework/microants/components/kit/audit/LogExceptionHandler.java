package yyc.open.framework.microants.components.kit.audit;

import com.lmax.disruptor.ExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Disruptor exception handler.
 *
 * @author Elias (siran0611@gmail.com)
 */
public final class LogExceptionHandler<T> implements ExceptionHandler<T> {

	private static final Logger LOG = LoggerFactory.getLogger(LogExceptionHandler.class);

	public interface OnEventException<T> {

		void onException(T event, Throwable ex);
	}

	private final String name;
	private final OnEventException<T> onEventException;

	public LogExceptionHandler(String name) {
		this(name, null);
	}

	public LogExceptionHandler(String name, OnEventException<T> onEventException) {
		this.name = name;
		this.onEventException = onEventException;
	}

	@Override
	public void handleOnStartException(Throwable throwable) {
		LOG.error("Fail to start {} disruptor.", this.name, throwable);
	}

	@Override
	public void handleOnShutdownException(Throwable ex) {
		LOG.error("Fail to shutdown {} disruptor", this.name, ex);
	}

	@Override
	public void handleEventException(Throwable ex, long sequence, T event) {
		LOG.error("Handle {} disruptor event error, event is {}", this.name, event, ex);
		if (this.onEventException != null) {
			this.onEventException.onException(event, ex);
		}
	}
}
