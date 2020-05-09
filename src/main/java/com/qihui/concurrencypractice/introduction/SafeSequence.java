package com.qihui.concurrencypractice.introduction;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

/**
 * @author chenqihui
 * @date 2020/5/9
 */
@ThreadSafe
public class SafeSequence implements Sequence {
    @GuardedBy("this")
    private int value;

    @Override
    public synchronized int getNext() {
        return value++;
    }
}
