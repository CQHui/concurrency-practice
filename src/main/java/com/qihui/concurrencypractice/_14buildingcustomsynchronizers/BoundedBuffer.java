package com.qihui.concurrencypractice._14buildingcustomsynchronizers;

/**
 * Bounded buffer using condition queues.
 */
public class BoundedBuffer<V> extends BaseBoundedBuffer<V> {
    //CONDITION PREDICATE: not-full (!isFull())
    //CONDITION PREDICATE: not-empty (!isEmpty())


    public BoundedBuffer(int size) {
        super(size);
    }

    public synchronized void put(V v) throws InterruptedException {
        while (isFull()) {
            wait();
        }
        doPut(v);
        notifyAll();
    }

    public synchronized V take() throws InterruptedException {
        while (isEmpty()) {
            wait();
        }
        V v = doTake();
        notifyAll();
        return v;
    }
}
