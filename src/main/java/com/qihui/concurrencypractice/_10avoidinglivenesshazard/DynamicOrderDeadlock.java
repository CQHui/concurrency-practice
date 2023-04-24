package com.qihui.concurrencypractice._10avoidinglivenesshazard;

import java.math.BigDecimal;


/**
 * @author chenqihui
 */
public class DynamicOrderDeadlock {
    /**
     * Deadlock will occur when
     * transferMoney(me, you, 10), at the same time
     * transferMoney(you, me, 20)
     */
    public void transferMoney(Account fromAccount, Account toAccount, BigDecimal amount) {
        synchronized (fromAccount) {
            synchronized (toAccount) {
                fromAccount.debit(amount);
                toAccount.credit(amount);
            }
        }
    }

    /**
     * using this object as a lock to ensure only one thread at a time can be performed when the hash of from and to are equal
     * if account has a unique, immutable, comparable key, this is not necessary
     */
    public static final Object tieLock = new Object();

    /**
     * Inducing a lock ordering to avoid deadlock
     */
    public void concurrentTransferMoney(Account fromAccount, Account toAccount, BigDecimal amount) {
        class Helper {
            public void transfer() {
                fromAccount.debit(amount);
                toAccount.credit(amount);
            }
        }

        int fromHash = System.identityHashCode(fromAccount);
        int toHash = System.identityHashCode(toAccount);

        if (fromHash < toHash) {
            synchronized (fromAccount) {
                synchronized (toAccount) {
                    new Helper().transfer();
                }
            }
        } else if (fromHash > toHash) {
            synchronized (toAccount) {
                synchronized (fromAccount) {
                    new Helper().transfer();
                }
            }
        } else {
            synchronized (tieLock) {
                synchronized (fromAccount) {
                    synchronized (toAccount) {
                        new Helper().transfer();
                    }
                }
            }
        }
    }
}

class Account {
    public void debit(BigDecimal amount) {}

    public void credit(BigDecimal amount) {}
}

