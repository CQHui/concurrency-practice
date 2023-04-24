package com.qihui.concurrencypractice._15nonblockingsynchronization;

import net.jcip.annotations.ThreadSafe;

/**
 * @author chenqihui
 * @date 2020/9/13
 */
@ThreadSafe
public class SimulatedCAS {
    private int value;

    public synchronized int get() {
        return value;
    }

    public  synchronized  int compareAndSwap(int expectedValue, int newValue) {
        int oldValue = value;
        if (oldValue == expectedValue) {
            value = newValue;
        }
        return oldValue;
    }

    public synchronized boolean compareAndSet(int expectedValue, int newValue) {
        return (expectedValue == compareAndSwap(expectedValue, newValue));
    }
}
