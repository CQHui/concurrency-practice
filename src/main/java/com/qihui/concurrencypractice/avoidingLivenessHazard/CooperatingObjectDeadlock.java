package com.qihui.concurrencypractice.avoidingLivenessHazard;

import java.util.HashSet;
import java.util.Set;

/**
 * Invoking an alien method with a lock held is asking for liveness trouble,
 * The alien method might acquire other locks(risking deadlock) or block for an unexpectedly long time.
 */
public class CooperatingObjectDeadlock {

}

class Taxi {
    private Point location, destination;
    private final Dispatcher dispatcher;

    public Taxi(Dispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    public synchronized void setLocation(Point location) {
        this.location = location;
        if (location.equals(destination)) {
            dispatcher.notifyAvaliable(this);
        }
    }

    public Point getLocation() {
        return location;
    }
}

class Dispatcher {
    private final Set<Taxi> taxis;
    private final Set<Taxi> avaliableTaxis;

    public Dispatcher() {
        taxis = new HashSet<>();
        avaliableTaxis = new HashSet<>();
    }

    public synchronized void notifyAvaliable(Taxi taxi) {
        avaliableTaxis.add(taxi);
    }

    public synchronized Image getImage() {
       Image image = new Image();
        for (Taxi taxi : taxis) {
            image.drawMarker(taxi.getLocation());
        }
        return image;
    }
}

class SafeTaxi {
    private Point location, destination;
    private final SafeDispatcher dispatcher;

    public SafeTaxi(SafeDispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    public void setLocation(Point location) {
        boolean reachedDestination;
        synchronized (this) {
            this.location = location;
            reachedDestination = location.equals(destination);
        }
        if (reachedDestination) {
            dispatcher.notifyAvaliable(this);
        }

    }

    public Point getLocation() {
        return location;
    }
}

class SafeDispatcher {
    private final Set<SafeTaxi> taxis;
    private final Set<SafeTaxi> avaliableTaxis;

    public SafeDispatcher() {
        taxis = new HashSet<>();
        avaliableTaxis = new HashSet<>();
    }

    public synchronized void notifyAvaliable(SafeTaxi taxi) {
        avaliableTaxis.add(taxi);
    }

    public Image getImage() {
        Set<SafeTaxi> copy;
        synchronized (this) {
            copy = new HashSet<>(taxis);
        }
        Image image = new Image();
        for (SafeTaxi taxi : copy) {
            image.drawMarker(taxi.getLocation());
        }
        return image;
    }
}

class Point {

}

class Image {
    void drawMarker(Point location) {}
}
