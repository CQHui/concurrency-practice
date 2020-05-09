package com.qihui.concurrencypractice.introduction;

import net.jcip.annotations.NotThreadSafe;

/**
 * @author chenqihui
 * @date 2020/5/9
 */
@NotThreadSafe
public class UnsafeSequence implements Sequence {
    private int value;

    @Override
    public int getNext() {
        return value++;
    }
}
