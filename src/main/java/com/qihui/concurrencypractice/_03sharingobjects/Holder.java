package com.qihui.concurrencypractice._03sharingobjects;

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
            throw new RuntimeException("This statement is false");
        }
    }
}
