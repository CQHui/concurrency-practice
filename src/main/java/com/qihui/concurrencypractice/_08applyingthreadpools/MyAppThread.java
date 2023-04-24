package com.qihui.concurrencypractice._08applyingthreadpools;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Set a custom UncaughtExceptionHandler that write a message to logger.
 */
public class MyAppThread extends Thread {
    public static final String DEFAULT_VALUE = "MyThread";
    private static volatile boolean debugLifeCycle = false;
    private static final AtomicInteger created = new AtomicInteger();
    private static final AtomicInteger alive = new AtomicInteger();
    private static final Logger log = Logger.getAnonymousLogger();

    public MyAppThread(Runnable runnable) {
        new MyAppThread(runnable, DEFAULT_VALUE);
    }

    public MyAppThread(Runnable runnable, String name) {
        super(runnable, name + "-" + created.incrementAndGet());
        setUncaughtExceptionHandler(
                (t, e) -> log.log(Level.SEVERE, "Uncaught in thread " + t.getName(), e)
        );
    }
    @Override
    public void run() {
        boolean debug = debugLifeCycle;
        if (debug) {
            log.log(Level.FINE, "Created " + getName());
        }
        try {
            alive.incrementAndGet();
            super.run();
        } finally {
            alive.decrementAndGet();
            if (debug) {
                log.log(Level.FINE, "Created " + getName());
            }
        }
    }

    public static boolean isDebugLifeCycle() {
        return debugLifeCycle;
    }

    public static int getCreated() {
        return created.get();
    }

    public static int getAlive() {
        return alive.get();
    }

    public static void setDebugLifeCycle(boolean debugLifeCycle) {
        MyAppThread.debugLifeCycle = debugLifeCycle;
    }
}
