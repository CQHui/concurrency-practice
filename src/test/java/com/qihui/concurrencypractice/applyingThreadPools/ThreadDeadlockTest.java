package com.qihui.concurrencypractice.applyingThreadPools;

import com.qihui.concurrencypractice.applyingThreadPools.ThreadDeadlock.RenderPageTask;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

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
}
