package ru.temon137.labyrintharium;


import java.util.ArrayList;
import java.util.List;

import ru.temon137.labyrintharium.World.GameObjects.Beings.Being;
import ru.temon137.labyrintharium.World.TriggerManager;
import ru.temon137.labyrintharium.World.World;


public class MainThread extends Thread {
    private boolean running;

    public MainThread() {
        super();
        running = false;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    @Override
    public void run() {
        while (running) {
            TriggerManager.getCurrentTriggerManager().callAllTriggers();

            List<Being> beings = new ArrayList<>(World.getBeingsMap().getAllT());
            for (Being being : beings) {
                if (being.isSpawned()) {
                    being.action();
                }
            }
        }
    }
}
