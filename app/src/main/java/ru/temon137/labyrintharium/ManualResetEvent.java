package ru.temon137.labyrintharium;


public class ManualResetEvent {
    private final Object monitor = new Object();
    private volatile boolean open = false;
    //=============


    public ManualResetEvent(boolean open) {
        this.open = open;
    }

    public void waitOne() throws InterruptedException {
        synchronized (monitor) {
            while (!open) {
                monitor.wait();
            }
        }
    }

    public void set() {
        synchronized (monitor) {
            open = true;
            monitor.notify();
        }
    }

    public void reset() {
        open = false;
    }
}