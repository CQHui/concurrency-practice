package com.qihui.concurrencypractice._15nonblockingsynchronization;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @author chenqihui
 * @date 2020/9/13
 */
@ThreadSafe
public class LinkedQueue <E> {
    private static class Node<E> {
        E item;
        final AtomicReference<Node<E>> next;

        Node(E element, Node<E> next) {
            this.item = element;
            this.next = new AtomicReference<>(next);
        }
    }

    private final Node<E> dummy = new Node<E>(null, null);
    private final AtomicReference<Node<E>> head = new AtomicReference<>(dummy);
    private final AtomicReference<Node<E>> tail = new AtomicReference<>(dummy);

    public boolean put(E item) {
        Node<E> newNode = new Node<>(item, null);
        while (true) {
            Node<E> curTail = tail.get();
            Node<E> tailNext = curTail.next.get();
            if (curTail == tail.get()) {
                if (tailNext == null) {
                    if (curTail.next.compareAndSet(null, newNode)) {
                        tail.compareAndSet(curTail, newNode);
                        return true;
                    }
                } else {
                    tail.compareAndSet(curTail, tailNext);
                }
            }
        }
    }

}
