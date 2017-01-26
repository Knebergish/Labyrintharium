package ru.temon137.labyrintharium;


public class ManualResetEvent {
    private final Object monitor = new Object();
    private volatile boolean open = false;
    //=============


    public ManualResetEvent(boolean open) {
        this.open = open;
    }

    public void waitOne() {
        synchronized (monitor) {
            while (!open) {
                try {
                    monitor.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException("В MRE ошибочка.");
                }
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