package com.qihui.concurrencypractice.cancellationAndShutdown.stoppingThreads;

import java.io.PrintWriter;
import java.util.concurrent.BlockingQueue;

/**
 * @author chenqihui
 * @date 2020/6/16
 */
public class LogService {
    private final BlockingQueue<String> queue;
    private final LoggerThread loggerThread;
    private final PrintWriter printWriter;
    private boolean isShutdown;
    private int reservations;

    public LogService(BlockingQueue<String> queue, LoggerThread loggerThread, PrintWriter printWriter) {
        this.queue = queue;
        this.loggerThread = loggerThread;
        this.printWriter = printWriter;
    }

    public void start() {
        loggerThread.start();
    }

    public void stop() {
        synchronized (this) {
            isShutdown = true;
        }
        loggerThread.interrupt();
    }

    public void log(String msg) throws InterruptedException {
        synchronized (this) {
            if (isShutdown) {
                throw new IllegalStateException();
            }
            ++reservations;
        }
        queue.put(msg);
    }

    public class LoggerThread extends Thread {
        @Override
        public void run() {
            new LogRunnable().run();
        }
    }
    public class LogRunnable implements Runnable {
        @Override
        public void run() {
            try {
                while (true) {
                    synchronized (LogService.this) {
                        if (isShutdown && reservations == 0) {
                            break;
                        }
                    }
                    String message = null;
                    try {
                        message = queue.take();
                    } catch (InterruptedException e) {
                        //retry
                        continue;
                    }
                    synchronized (LogService.this) {
                        --reservations;
                    }
                    printWriter.println(message);
                }
            } finally {
                printWriter.close();
            }
        }
    }
}




