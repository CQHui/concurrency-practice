package com.qihui.concurrencypractice.testingConcurrentProgram;

import com.qihui.concurrencypractice.testingConcurrentPrograms.BoundedBuffer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author chenqihui
 * @date 2020/8/30
 */
public class BoundedBufferTest {
    @Test
    void testIsEmptyWhenConstructed() {
        BoundedBuffer<Integer> bb = new BoundedBuffer<>(10);
        assertTrue(bb.isEmpty());
        assertFalse(bb.isFull());
    }

    @Test
    void testIsFullAfterPuts() throws InterruptedException {
        BoundedBuffer<Integer> bb = new BoundedBuffer<>(10);
        for (int i = 0; i < 10; i++) {
            bb.put(i);
        }
        assertFalse(bb.isEmpty());
        assertTrue(bb.isFull());
    }

    @Test
    void testTakeBlocksWhenEmpty() {
        int LOCKUP_DETECT_TIMEOUT = 1000;
        BoundedBuffer<Integer> bb = new BoundedBuffer<>(10);
        Thread thread = new Thread(() -> {
            try {
                Integer unused = bb.take();
                fail();
            } catch (InterruptedException e) {
            }
        });

        try {
            thread.start();
            Thread.sleep(LOCKUP_DETECT_TIMEOUT);
            thread.interrupt();
            thread.join(LOCKUP_DETECT_TIMEOUT);
            assertFalse(thread.isAlive());
        } catch (InterruptedException e) {
            fail();
        }
    }
}
