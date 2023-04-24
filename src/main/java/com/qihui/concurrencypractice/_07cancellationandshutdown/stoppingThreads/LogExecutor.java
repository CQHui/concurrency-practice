package com.qihui.concurrencypractice._07cancellationandshutdown.stoppingThreads;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @author chenqihui
 * @date 2020/6/19
 */
public class LogExecutor {
    private final ExecutorService exec;

    public LogExecutor(ExecutorService exec) {
        this.exec = exec;
    }

    public void log(String msg) {
        try {
            exec.execute(new WriteTask(msg));
        } catch (RejectedExecutionException e) {
            e.printStackTrace();
        }
    }

    public void stop() throws InterruptedException {
        exec.shutdown();
        exec.awaitTermination(1000L, TimeUnit.MILLISECONDS);
    }


}

class WriteTask implements Runnable {
    String msg;

    public WriteTask(String msg) {
        this.msg = msg;
    }

    @Override
    public void run() {
        System.out.println(msg);
    }
}


