package com.qihui.concurrencypractice._14buildingcustomsynchronizers;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Simple SemaphoreLock implemented
 */
public class SemaphoreOnLock {
    private final Lock lock = new ReentrantLock();
    private final Condition permitAvailable = lock.newCondition();
    private int permit;

    SemaphoreOnLock(int initialPermits) {
        lock.lock();
        try {
            permit = initialPermits;
        } finally {
            lock.unlock();
        }
    }

    public void acquire() throws InterruptedException {
        lock.lock();
        try {
            while (permit <= 0) {
                permitAvailable.await();
            }
            --permit;
        } finally {
            lock.unlock();
        }
    }

    public void release() {
        lock.lock();
        try {
            ++permit;
            permitAvailable.signal();
        } finally {
            lock.unlock();
        }
    }
}
