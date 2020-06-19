package com.qihui.concurrencypractice.cancellationAndShutdown.stoppingThreads;

import java.awt.*;
import java.io.File;
import java.io.FileFilter;
import java.util.concurrent.BlockingQueue;

/**
 * Work only when the number of producers and consumers is known,
 */
public class indexingService {
    private static final File POISON = new File("");
    private final IndexerThread consumer = new IndexerThread();
    private final CrawlerThread produced = new CrawlerThread();
    private final BlockingQueue<File> queue;
    private final FileFilter fileFilter;
    private final File root;

    public indexingService(BlockingQueue<File> queue, FileFilter fileFilter, File root) {
        this.queue = queue;
        this.fileFilter = fileFilter;
        this.root = root;
    }

    public void start() {
        produced.start();
        consumer.start();
    }

    public void stop() {
        produced.interrupt();
    }

    public void awaitTermination() throws InterruptedException {
        consumer.join();
    }

    class CrawlerThread extends Thread {
        @Override
        public void run() {
            try {
                crawl(root);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                while(true) {
                    try {
                        queue.put(POISON);
                        break;
                    } catch (InterruptedException e) {
                        //retry
                    }
                }
            }
        }
    }

    class IndexerThread extends Thread {
        @Override
        public void run() {
            try {
                while (true) {
                    File file = queue.take();
                    if (file == POISON) {
                        break;
                    } else {
                        indexFile(file);
                    }
                }
            } catch (InterruptedException consumed) {
                //retry
            }

        }
    }

    private void crawl(File root) throws InterruptedException{
        //traverse root and put them to queue then
        queue.put(root);
    }
    private void indexFile(File file) {
    }
}


