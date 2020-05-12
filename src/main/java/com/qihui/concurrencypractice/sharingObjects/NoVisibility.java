package com.qihui.concurrencypractice.sharingObjects;

import lombok.SneakyThrows;

/**
 * Visibility
 * volatile can only guarantee visibility, but can not guarantee atomicity
 */
public class NoVisibility {
    /**
     * Use keyword volatile only when all the following criteria are met:
     * - Writes to the variable do not depend on its current value. or you can ensure that only a single thread updates this value.]
     * - The variable does not participate in invariants with other state variable.
     * - Locking is not required for any other reason while the variable is being accessed.
     */
    private volatile static boolean ready;
    private static int number = 0;

    private static class ReaderThread extends Thread {
        @SneakyThrows
        @Override
        public void run() {
            while (!ready) {
                //very confused here. this code is pasted from book, but it seems this thread yield, and run it again then,
                // thread will refresh the main memory. In other word, it won't be a loop for ever when we use Thread.yield()
//                Thread.yield();
            }
            System.out.println(number);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new ReaderThread().start();
        Thread.sleep(1000L);
        number = 42;
        ready = true;
    }
}
