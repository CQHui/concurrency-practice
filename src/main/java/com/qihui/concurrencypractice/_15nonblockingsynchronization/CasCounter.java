package com.qihui.concurrencypractice._15nonblockingsynchronization;

import net.jcip.annotations.ThreadSafe;

/**
 * Nonblocking Counter using CAS
 */
@ThreadSafe
public class CasCounter {
    private SimulatedCAS value;

    public int getValue() {
        return value.get();
    }

    public int increment() {
        int v;
        do {
            v = value.get();
        } while (v != value.compareAndSwap(v, v + 1));
        return v + 1;
    }
}
