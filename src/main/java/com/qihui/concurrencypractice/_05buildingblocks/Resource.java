package com.qihui.concurrencypractice._05buildingblocks;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author chenqihui
 * @date 12/20/20
 */
public class Resource {
    private BlockingQueue blockingQueue;
    private boolean FLAG = true;
    private volatile AtomicInteger number = new AtomicInteger(0);
    private Object lock = new Object();

    public Resource(BlockingQueue blockingQueue) {
        this.blockingQueue = blockingQueue;
    }

    public void produce() throws InterruptedException {
        Object o = new Object();
        while (FLAG) {
                boolean offer = blockingQueue.offer(o, 2, TimeUnit.SECONDS);
                if (offer) {
                    System.out.println(Thread.currentThread().getName() + number.incrementAndGet());
                } else {
                    System.out.println(Thread.currentThread().getName() + "生产失败");
                }
            TimeUnit.SECONDS.sleep(1);

        }

    }

    public void consume() throws InterruptedException {
        while (FLAG) {
                Object poll = blockingQueue.poll(2, TimeUnit.SECONDS);
                if (poll == null) {
                    System.out.println(Thread.currentThread().getName() + "消费失败");
                } else {
                    System.out.println(Thread.currentThread().getName() + number.incrementAndGet());
                }

            TimeUnit.SECONDS.sleep(1);
        }
    }

    public void stop() {
        FLAG = false;
    }

    public static void main(String[] args) throws InterruptedException {
        Resource resource = new Resource(new LinkedBlockingQueue(5));
        new Thread(() -> {
            try {
                resource.produce();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "A").start();

        new Thread(() -> {
            try {
                resource.consume();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "B").start();


        while (resource.number.get() < 100) {
        }
        resource.stop();


        System.out.println(resource.blockingQueue.size());
    }
}
