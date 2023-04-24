package com.qihui.concurrencypractice._08applyingthreadpools;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Deadlock in a single-thread Executor. Don't do this
 */
public class ThreadDeadlock {
    ExecutorService executorService = Executors.newSingleThreadExecutor();

    public RenderPageTask getRenderPageTask() {
        return new RenderPageTask();
    }

    public class RenderPageTask implements Callable<String> {

        @Override
        public String call() throws Exception {
            Future<String> header, footer;
            header = executorService.submit(() -> {
                String headerContent = "header";
                System.out.println(headerContent);
                Thread.sleep(1000L);
                return headerContent;
            });
            footer = executorService.submit(() -> {
                String footerContent = "footer";
                System.out.println(footerContent);
                Thread.sleep(1000L);
                return footerContent;
            });
            return header.get() + " and " +  footer.get();
        }
    }

}
