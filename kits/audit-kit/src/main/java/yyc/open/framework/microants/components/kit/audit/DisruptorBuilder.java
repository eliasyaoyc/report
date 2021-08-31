package yyc.open.framework.microants.components.kit.audit;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.WaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import java.util.concurrent.ThreadFactory;
import lombok.Getter;
import yyc.open.framework.microants.components.kit.audit.util.NamedThreadFactory;
import yyc.open.framework.microants.components.kit.common.validate.Asserts;

/**
 * A builder to build a disruptor instance.
 *
 * @author Elias (siran0611@gmail.com)
 */
@Getter
public class DisruptorBuilder<T> {

	private EventFactory<T> eventFactory;
	private Integer ringBufferSize;
	private ThreadFactory threadFactory = new NamedThreadFactory("Disruptor", true);
	private ProducerType producerType = ProducerType.MULTI;
	private WaitStrategy waitStrategy = new BlockingWaitStrategy();

	private DisruptorBuilder() {
	}

	public static <T> DisruptorBuilder<T> newInstance() {
		return new DisruptorBuilder<>();
	}

	public DisruptorBuilder<T> setEventFactory(final EventFactory<T> eventFactory) {
		this.eventFactory = eventFactory;
		return this;
	}

	public DisruptorBuilder<T> setRingBufferSize(final int ringBufferSize) {
		this.ringBufferSize = ringBufferSize;
		return this;
	}

	public DisruptorBuilder<T> setThreadFactory(final ThreadFactory threadFactory) {
		this.threadFactory = threadFactory;
		return this;
	}

	public DisruptorBuilder<T> setProducerType(final ProducerType producerType) {
		this.producerType = producerType;
		return this;
	}

	public DisruptorBuilder<T> setWaitStrategy(final WaitStrategy waitStrategy) {
		this.waitStrategy = waitStrategy;
		return this;
	}

	public Disruptor<T> build() {
		Asserts.notNull(ringBufferSize, " Ring Buffer size not set.");
		Asserts.notNull(eventFactory, "Event factory not set.");
		return new Disruptor<T>(eventFactory, ringBufferSize, threadFactory, producerType, waitStrategy);
	}
}
