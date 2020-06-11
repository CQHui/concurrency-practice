package com.qihui.concurrencypractice.cancellationAndShutdown;

import java.math.BigInteger;
import java.util.concurrent.BlockingQueue;

/**
 * @author chenqihui
 * @date 2020/6/11
 */
public class PrimeProducer extends Thread {
    private final BlockingQueue<BigInteger> queue;

    public PrimeProducer(BlockingQueue<BigInteger> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        BigInteger one = BigInteger.ONE;
        while (!Thread.currentThread().isInterrupted()) {
            try {
                queue.put(one.nextProbablePrime());
            } catch (InterruptedException e) {
                /* Allow thread to exit */
                return;
            }
        }
        super.run();
    }
}
