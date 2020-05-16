package com.qihui.concurrencypractice.composingObjects;

import net.jcip.annotations.NotThreadSafe;
import net.jcip.annotations.ThreadSafe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author chenqihui
 * @date 2020/5/16
 */
@ThreadSafe
public class ListHelper<E> {
    public List<E> list = Collections.synchronizedList(new ArrayList<>());

    public boolean putIfAbsent(E x) {
        synchronized (list) {
            boolean absent = !list.contains(x);
            if (absent) {
                list.add(x);
            }
            return absent;
        }
    }
}

@NotThreadSafe
class ListHelperNotSafe<E> {
    public List<E> list = Collections.synchronizedList(new ArrayList<>());

    //should guard list, not ListHelper. There is no guarantee that another thread won't modify the list
    // while putIfAbsent is excuting.
    public synchronized boolean putIfAbsent(E x) {
        boolean absent = !list.contains(x);
        if (absent) {
            list.add(x);
        }
        return absent;
    }
}
