package com.qihui.concurrencypractice.avoidingLivenessHazard;

/**
 * Don't do this.
 * Simple lock-ordering deadlock
 */
public class OrderingDeadlock {
    private final Object left = new Object();
    private final Object right = new Object();

    public void leftRight() {
        synchronized (left) {
            synchronized (right) {
                doSomething();
            }
        }
    }

    public void rightLeft() {
        synchronized (right) {
            synchronized (left) {
                doSomething();
            }
        }
    }

    private void doSomething() {
    }
}
