package com.qihui.concurrencypractice._01introduction;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author chenqihui
 * @date 2020/5/9
 */
@Slf4j
public class SafeSequenceTest {

    /**
     * it may fail sometimes, because the class {@link UnsafeSequence}  is not thread safe
     * @throws InterruptedException
     */
    @Test
    public void testUnsafeSequence() throws InterruptedException {
        UnsafeSequence unsafeSequence = new UnsafeSequence();
        validateSafeSequence(unsafeSequence);
    }

    @Test
    public void testSafeSequence() throws InterruptedException {
        SafeSequence sequence = new SafeSequence();
        validateSafeSequence(sequence);
    }

    private void validateSafeSequence(Sequence sequence) throws InterruptedException {
        Set<Integer> set = Collections.synchronizedSet(new HashSet<>());
        ExecutorService executorService = Executors.newFixedThreadPool(50);
        int elementsNum = 1000;
        for (int i = 0; i < elementsNum; i++) {
            executorService.execute(() -> {
                int next = sequence.getNext();
                set.add(next);
            });
        }
        Thread.sleep(4000L);
        Assert.isTrue(set.size() == elementsNum,
                "there are some elements duplicated. The Set only contain " + set.size() + "elements, should be " + elementsNum);
    }
}
