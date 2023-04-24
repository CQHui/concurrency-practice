package com.qihui.concurrencypractice._13explicitlocks;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author chenqihui
 * @date 2020/9/12
 */
public class TryLockTimeOutExample {
    private final Lock lock = new ReentrantLock();
    /**
     * Locking with a time budget
     */
    public boolean trySendOnSharedLine(String message, long timeout, TimeUnit unit) throws InterruptedException {
        if (!lock.tryLock(timeout, unit)) {
            return false;
        }
        try {
            return sendOnSharedLine(message);
        } finally {
            lock.unlock();
        }
    }

    /**
     * Interruptibe lock acquisition
     */
    public boolean sendOnSharedLine(String message) throws InterruptedException {
        lock.lockInterruptibly();
        try {
            return cancellableSendOnSharedLine(message);
        } finally {
            lock.unlock();
        }
    }

    private boolean cancellableSendOnSharedLine(String message) {
        return false;
    }
}
