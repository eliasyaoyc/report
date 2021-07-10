package yyc.open.framework.microants.components.enhance.collections;

import java.util.concurrent.BlockingQueue;

/**
 * {@link ResizeableBlockingQueue} Extends the {@link SizeBlockingQueue} to add the {@code adjustCapacity} method, which will adjust
 * the capacity by a certain amount towards a maximum or minimum.
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/7/10
 */
public class ResizeableBlockingQueue<E> extends SizeBlockingQueue<E> {
    private volatile int capacity;

    public ResizeableBlockingQueue(BlockingQueue<E> queue, int initialCapacity) {
        super(queue, initialCapacity);
        this.capacity = initialCapacity;
    }

    @Override
    public int capacity() {
        return this.capacity;
    }

    @Override
    public int remainingCapacity() {
        return Math.max(0, this.capacity());
    }

    /**
     * Resize the limit for the queue, returning the new size limit.
     */
    public synchronized int adjustCapacity(int optimalCapacity, int adjustmentAmount, int minCapacity, int maxCapacity) {
        assert adjustmentAmount > 0 : "adjustment amount should be a positive value";
        assert optimalCapacity >= 0 : "desired capacity cannot be negative";
        assert minCapacity >= 0 : "cannot have min capacity smaller than 0";
        assert maxCapacity >= minCapacity : "cannot have max capacity smaller than min capacity";

        if (optimalCapacity == capacity) {
            return this.capacity;
        }

        if (optimalCapacity > capacity + adjustmentAmount) {
            // adjust up
            final int newCapacity = Math.min(maxCapacity, capacity + adjustmentAmount);
            this.capacity = newCapacity;
            return newCapacity;
        } else if (optimalCapacity < capacity - adjustmentAmount) {
            final int newCapacity = Math.max(minCapacity, capacity - adjustmentAmount);
            this.capacity = newCapacity;
            return newCapacity;
        } else {
            return this.capacity;
        }
    }
}
