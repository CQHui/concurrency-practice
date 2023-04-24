package com.qihui.concurrencypractice._13explicitlocks;


import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author chenqihui
 * @date 2020/9/12
 */
public class TryLockExample {

    public boolean transferMoney(Account fromAcct, Account toAcct, BigDecimal amount, long timeout, TimeUnit unit) {
        long stopTime = System.nanoTime() + unit.toNanos(timeout);
        while (true) {
            if (fromAcct.lock.tryLock()) {
                try {
                    if (toAcct.lock.tryLock()) {
                        try {
                            fromAcct.debit(amount);
                            toAcct.credit(amount);
                            return true;
                        } finally {
                            toAcct.lock.unlock();
                        }
                    }
                } finally {
                    fromAcct.lock.unlock();
                }
            }
            if (System.nanoTime() > stopTime) {
                return false;
            }
        //Snooze for a while
        }
    }
}

class Account {
    public ReentrantLock lock;
    public void debit(BigDecimal amount) {}

    public void credit(BigDecimal amount) {}
}
