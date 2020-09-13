package com.qihui.concurrencypractice.nonblockingSynchronization;


import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * With low to moderate contention, atomic offers better scalability
 * With high contention, locks offer better contention avoidance
 */
public class ReentrantLockPseudoRandom {
    private final Lock lock = new ReentrantLock();
    private int seed;

    public ReentrantLockPseudoRandom(int seed) {
        this.seed = seed;
    }

    public int nextInt(int n) {
        lock.lock();
        try {
            int s = seed;
            seed = calculateNext(s);
            int remainder = s % n;
            return remainder > 0 ? remainder : remainder + n;
        } finally {
            lock.unlock();
        }
    }

    public static int calculateNext(int s) {
        //some logic is not ignored
        return 0;
    }
}

class AtomicPseudoRandom {
    private AtomicInteger seed;

    public AtomicPseudoRandom(int seed) {
        this.seed = new AtomicInteger(seed);
    }

    public int nextInt(int n) {
        while (true) {
            int s = seed.get();
            if (seed.compareAndSet(s, n)) {
                int remainder = s % n;
                return remainder > 0 ? remainder : remainder + n;
            }
        }
    }
}
