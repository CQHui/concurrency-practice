package com.qihui.concurrencypractice.sharingObjects;

import sun.jvm.hotspot.utilities.AssertionFailure;

/**
 * class at risk of failure if not properly published
 */
public class Holder {
    private int n;

    public Holder(int n) {
        this.n = n;
    }

    public void assertSanity() {
        if (n != n) {
            throw new AssertionFailure("This statement is false");
        }
    }
}
