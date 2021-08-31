package yyc.open.framework.microants.components.kit.audit;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.EventTranslator;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import java.util.List;
import javax.annotation.Nonnull;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import yyc.open.framework.microants.components.kit.audit.errors.AuditTooBusyException;

/**
 * The core class that be responsible for gather audit log entries and scatter to storage(e.g. mysql).
 *
 * @author Elias (siran0611@gmail.com)
 */
public class Audit {

	private static final Logger LOG = LoggerFactory.getLogger(Audit.class);

	private Disruptor<AuditEntryTask> disruptor;
	private RingBuffer<AuditEntryTask> taskQueue;
	private final List<AuditInterceptor> interceptors;
	private final List<AuditStorage> storages;

	protected Audit(@Nonnull final AuditOptions options) {
		this.disruptor = DisruptorBuilder.<AuditEntryTask>newInstance()
				.setEventFactory(new AuditTaskFactory())
				.setRingBufferSize(options.getDisruptorBufferSize())
				.setProducerType(ProducerType.MULTI)
				.setWaitStrategy(new BlockingWaitStrategy())
				.build();
		this.disruptor.handleEventsWith(new AuditTaskHandler());
		this.disruptor.setDefaultExceptionHandler(new LogExceptionHandler<Object>(this.getClass().getSimpleName()));
		this.taskQueue = this.disruptor.start();
		this.interceptors = AuditFactory.INSTANCE.getInterceptors();
		this.storages = AuditFactory.INSTANCE.getStorages();
	}

	/**
	 * Publish audit event to disruptor and wait to consume.
	 *
	 * @param entity need to storage entity.
	 */
	public void publishEvent(AuditEntity entity) {
		preHandleEntry(entity);
		this.publishEvent((task, seq) -> task.setEntity(entity));
	}

	private void publishEvent(final EventTranslator<AuditEntryTask> event) {
		if (!tryPublishEvent(event)) {
			throw new AuditTooBusyException("Audit handler is too busy.");
		}
	}

	private boolean tryPublishEvent(final EventTranslator<AuditEntryTask> event) {
		if (this.taskQueue.tryPublishEvent(event)) {
			return true;
		}
		return false;
	}

	private <T extends AuditEntity> void preHandleEntry(T entity) {
		if (CollectionUtils.isEmpty(this.interceptors)) {
			return;
		}
		this.interceptors.stream().forEach(inter -> inter.interceptor(entity));
	}

	private static class AuditTaskFactory implements EventFactory<AuditEntryTask> {

		@Override
		public AuditEntryTask newInstance() {
			return new AuditEntryTask();
		}
	}

	private class AuditTaskHandler implements EventHandler<AuditEntryTask> {

		/**
		 * Max committed index in current batch, reset to -1 every batch.
		 */
		private long maxCommittedIndex = -1;

		@Override
		public void onEvent(final AuditEntryTask auditEntry, final long sequence, final boolean endOfBatch) throws Exception {
			System.out.println(sequence);
			LOG.info("Audit Task Handler received task.");
			for (AuditStorage storage : storages) {
				storage.insert(auditEntry.getEntity());
			}
		}
	}
}



