package com.qihui.concurrencypractice.composingObjects;

import net.jcip.annotations.NotThreadSafe;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * delegate failed
 * If a class is composed of multiple independent thread-safe state variables and
 * has no operations that have any invalid state transitions, then it can delegate thread safely to the underlying state variables.
 */
@NotThreadSafe
public class NumberRange {

    //INVARIANT: lower <= upper
    private final AtomicInteger lower = new AtomicInteger(0);
    private final AtomicInteger upper = new AtomicInteger(0);

    public void setLower(int i) {
        //Warning --j unsafe check-then-act
        if (i > upper.get()) {
            throw new IllegalArgumentException("can't set lower to " + i + " > upper");
        }
        lower.set(i);
    }

    public void setUpper(int i) {
        //Warning --j unsafe check-then-act
        if (i < lower.get()) {
            throw new IllegalArgumentException("can't set lower to " + i + " > upper");
        }
        upper.set(i);
    }

    public boolean isInRange(int i) {
        return (i >= lower.get() && i <= upper.get());
    }
}
