package com.qihui.concurrencypractice._04composingobjects;

import jdk.nashorn.internal.ir.annotations.Immutable;
import net.jcip.annotations.ThreadSafe;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author chenqihui
 * @date 2020/5/15
 */
@ThreadSafe
public class DelegatingVehicleTracker {
    //If we had used MutablePoint class instead of Point, we would be breaking encapsulation by
    // letting getLocations publish a reference to mutable state that is not thread-safe.
    private final ConcurrentHashMap<String, Point> locations;
    private final Map<String, Point> unmodifiableMap;

    public DelegatingVehicleTracker(Map<String, Point> points) {
        this.locations = new ConcurrentHashMap<>(points);
        this.unmodifiableMap = Collections.unmodifiableMap(points);
    }

    public Map<String, Point> getLocations() {
        return unmodifiableMap;
    }

    public Point getLocation(String id) {
        return locations.get(id);
    }

    public void setLocations(String id, int x, int y) {
        if (locations.replace(id, new Point(x, y)) == null) {
            throw new IllegalArgumentException("invalid vehicle name " + id);
        }
    }
}

class MutablePoint {
    public int x, y;

    public MutablePoint() {
        x = 0;
        y = 0;
    }

    public MutablePoint(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

@Immutable
class Point {
    public final int x, y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
