package com.qihui.concurrencypractice.buildingCustomSynchronizers;

import net.jcip.annotations.ThreadSafe;

/**
 * Caller must be prepared to catch exception.
 * Not very pretty
 */
@ThreadSafe
public class GrumpyBoundedBuffer<V> extends BaseBoundedBuffer<V> {
    public GrumpyBoundedBuffer(int size) {
        super(size);
    }

    public synchronized void put(V v) {
        if (isFull()) {
            throw new IndexOutOfBoundsException("buffer is full");
        }
        doPut(v);
    }

    public synchronized V teak() {
        if(isEmpty()) {
            throw new NullPointerException("buffer is null");
        }
        return doTake();
    }
}

