package com.qihui.concurrencypractice.buildingBlocks.computable;

import java.util.Map;
import java.util.concurrent.*;

/**
 * @author chenqihui
 * @date 2020/5/29
 */
public class Memoizer4<A, V> implements Computable<A, V> {
    private final Map<A, Future<V>> cache = new ConcurrentHashMap<>();
    private final Computable<A, V> c;

    public Memoizer4(Computable<A, V> c) {
        this.c = c;
    }

    @Override
    public V compute(A arg) throws InterruptedException, ExecutionException {
        Future<V> vFuture = cache.get(arg);
        if (vFuture == null) {
            Callable<V> eval = () -> c.compute(arg);
            FutureTask<V> ft = new FutureTask<>(eval);
            vFuture = cache.putIfAbsent(arg, ft);
            if (vFuture == null) {
                vFuture = ft;
                ft.run();
            }
        }
        try {
            return vFuture.get();
        } catch (CancellationException e) {
            cache.remove(arg, vFuture);
            throw e;
        } catch (ExecutionException e) {
            throw e;
        }
    }
}
