package yyc.open.framework.microants.components.enhance.collections;

import java.util.AbstractQueue;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * {@link SizeBlockingQueue}
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/7/10
 */
public class SizeBlockingQueue<E> extends AbstractQueue<E> implements BlockingQueue<E> {
    private final BlockingQueue<E> queue;
    private final int capacity;

    private final AtomicInteger size = new AtomicInteger();

    public SizeBlockingQueue(BlockingQueue<E> queue, int capacity) {
        this.queue = queue;
        this.capacity = capacity;
    }

    @Override
    public int size() {
        return size.get();
    }

    public int capacity() {
        return this.capacity;
    }

    @Override
    public Iterator<E> iterator() {
        return null;
    }


    @Override
    public void put(E e) throws InterruptedException {

    }

    @Override
    public boolean offer(E e, long timeout, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public E take() throws InterruptedException {
        return null;
    }

    @Override
    public E poll(long timeout, TimeUnit unit) throws InterruptedException {
        return null;
    }

    @Override
    public int remainingCapacity() {
        return 0;
    }

    @Override
    public int drainTo(Collection<? super E> c) {
        return 0;
    }

    @Override
    public int drainTo(Collection<? super E> c, int maxElements) {
        return 0;
    }

    @Override
    public boolean offer(E e) {
        return false;
    }

    @Override
    public E poll() {
        return null;
    }

    @Override
    public E peek() {
        return null;
    }
}
