package com.qihui.concurrencypractice.taskExecution;

import net.jcip.annotations.ThreadSafe;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Don't use it
 * 1. Timer creates a single thread for executing timer tasks.
 * 2. Timer doesn't catch exception
 */
public class OutOfTimer {
    public static void main(String[] args) throws InterruptedException {
        Timer timer = new Timer();
        timer.schedule(new ThrowTask(), 1);
        Thread.sleep(1000L);
        timer.schedule(new NormalTask(), 1);
        Thread.sleep(5000L);
    }

    static class ThrowTask extends TimerTask {

        @Override
        public void run() {
            throw new RuntimeException();
        }
    }

    static class NormalTask extends TimerTask {

        @Override
        public void run() {
            System.out.println("doing something");
        }
    }
}
