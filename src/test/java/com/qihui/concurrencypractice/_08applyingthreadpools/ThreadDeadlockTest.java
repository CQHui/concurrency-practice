package com.qihui.concurrencypractice._08applyingthreadpools;

import com.qihui.concurrencypractice._14buildingcustomsynchronizers.BoundedBuffer;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import java.util.concurrent.*;

/**
 * @author chenqihui
 * @date 2020/7/5
 */
public class ThreadDeadlockTest {

    @Test
    public void testThreadDeadlock() throws ExecutionException, InterruptedException {
        ThreadDeadlock threadDeadlock = new ThreadDeadlock();
        Future<String> submit = threadDeadlock.executorService.submit(threadDeadlock.getRenderPageTask());
        Assert.notNull(submit.get(), "result should not be empty");
    }

    @Test
    public void testBoundedBuffer() throws InterruptedException {
        BoundedBuffer buffer = new BoundedBuffer(2);
        ExecutorService putJob = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 4; i++) {
            putJob.execute(() -> {
                try {
                    long id = Thread.currentThread().getId();
                    buffer.put(id);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        Thread.sleep(2000L);
        buffer.take();
        Thread.sleep(2000L);

        for (int i = 0; i < buffer.getBuf().length; i++) {
            System.out.print(buffer.getBuf()[i] + " ");
        }
    }
}
