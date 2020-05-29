package com.qihui.concurrencypractice.buildingBlocks.computable;

import java.util.Map;
import java.util.concurrent.*;

/**
 * almost perfect. But check-then-act is not atomic sequence.
 * both two threads don't see the value in cache, and do computing at the same time.
 */
public class Memoizer3<A, V> implements Computable<A, V> {
    private final Map<A, Future<V>> cache = new ConcurrentHashMap<>();
    private final Computable<A, V> c;

    public Memoizer3(Computable<A, V> c) {
        this.c = c;
    }

    @Override
    public V compute(A arg) throws InterruptedException, ExecutionException {
        Future<V> vFuture = cache.get(arg);
        if (vFuture == null) {
            Callable<V> eval = () -> c.compute(arg);
            FutureTask<V> ft = new FutureTask<>(eval);
            vFuture = ft;
            cache.put(arg, ft);
            ft.run();
        }
        try {
            return vFuture.get();
        } catch (ExecutionException e) {
            throw e;
        }
    }

}
