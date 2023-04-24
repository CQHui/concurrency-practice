package com.qihui.concurrencypractice._05buildingblocks.computable;

import java.util.concurrent.ExecutionException;

/**
 * @author chenqihui
 * @date 2020/5/29
 */
public interface Computable<A, V> {
    V compute(A arg) throws InterruptedException, ExecutionException;
}
