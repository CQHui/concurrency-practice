package com.qihui.concurrencypractice._14buildingcustomsynchronizers;

/**
 * @author chenqihui
 * @date 2020/9/12
 */
public class BaseBoundedBuffer<V> {
    private final V[] buf;
    private int tail;
    private int head;
    private int count;

    @SuppressWarnings("unchecked")
    public BaseBoundedBuffer(int capacity) {
        this.buf = (V[]) new Object[capacity];
    }

    protected synchronized final void doPut(V v) {
        buf[tail] = v;
        System.out.println("set buf[" + tail + "] to " + v);
        if (++tail == buf.length) {
            tail = 0;
        }
        ++count;
    }

    protected synchronized final V doTake() {
        V v = buf[head];
        buf[head] = null;
        if (++head == buf.length) {
            head = 0;
        }
        --count;
        return v;
    }

    public synchronized final boolean isFull() {
        return count == buf.length;
    }

    public synchronized final boolean isEmpty() {
        return count == 0;
    }

    public V[] getBuf() {
        return buf;
    }
}
