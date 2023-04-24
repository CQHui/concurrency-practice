package com.qihui.concurrencypractice._14buildingcustomsynchronizers;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * using signal instead of signalAll reducess the number of context switches
 * and lock acquisition by each buffer operation
 */
@ThreadSafe
public class ConditionBoundedBuffer<T> {
    protected final Lock lock = new ReentrantLock();
    //CONDITION PREDICATE: not-full (!isFull())
    private final Condition notFull = lock.newCondition();
    //CONDITION PREDICATE: not-empty (!isEmpty())
    private final Condition notEmpty = lock.newCondition();

    private final T[] items;
    private int tail, head, count;

    @SuppressWarnings("unchecked")
    public ConditionBoundedBuffer(int capacity) {
        this.items = (T[]) new Object[capacity];
    }

    public void put(T x) throws InterruptedException {
        lock.lock();
        try {
            while (count == items.length) {
                notFull.await();
            }
            items[tail] = x;
            if (++tail == items.length) {
                tail = 0;
            }
            ++count;
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    public T take() throws InterruptedException {
        lock.lock();
        try {
            while (count == 0) {
                notEmpty.await();
            }
            T x = items[head];
            items[head] = null;
            if (++head == items.length) {
                head = 0;
            }
            --count;
            notFull.signal();
            return x;
        } finally {
          lock.unlock();
        }
    }
}
