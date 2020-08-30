package com.qihui.concurrencypractice.performanceAndScalability;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashSet;
import java.util.Set;

/**
 * Reduce the frequency with which locks are requested
 * Reducing lock granularity
 */
@ThreadSafe
public class ServerStatus {
    @GuardedBy("this")
    private final Set<String> users = new HashSet<>();
    @GuardedBy("this")
    private final Set<String> queries = new HashSet<>();

    public synchronized void addUser(String u) {
        users.add(u);
    }

    public synchronized void addQueries(String q) {
        queries.add(q);
    }

    public synchronized void removeUser(String u) {
        users.remove(u);
    }

    public synchronized void removeQueries(String q) {
        queries.remove(q);
    }
}

@ThreadSafe
class BetterServerStatus {
    @GuardedBy("this")
    private final Set<String> users = new HashSet<>();
    @GuardedBy("this")
    private final Set<String> queries = new HashSet<>();

    public void addUser(String u) {
        synchronized (users) {
            users.add(u);
        }
    }

    public void addQueries(String q) {
        synchronized (queries) {
            queries.add(q);
        }
    }

    public synchronized void removeUser(String u) {
        synchronized (users) {
            users.remove(u);
        }
    }

    public synchronized void removeQueries(String q) {
        synchronized (queries) {
            queries.remove(q);
        }
    }
}


