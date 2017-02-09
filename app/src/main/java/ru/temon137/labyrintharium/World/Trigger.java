package ru.temon137.labyrintharium.World;


public abstract class Trigger {
    private int number;
    private boolean isActive;


    private Trigger() {
    }

    public Trigger(int number, boolean isActive) {
        this.number = number;
        this.isActive = isActive;
    }

    public int getNumber() {
        return number;
    }

    public boolean getActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }


    public abstract void action();
}
