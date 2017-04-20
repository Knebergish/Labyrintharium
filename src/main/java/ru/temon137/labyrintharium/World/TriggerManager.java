package ru.temon137.labyrintharium.World;


import java.util.ArrayList;
import java.util.List;


public class TriggerManager {
    private static TriggerManager currentTriggerManager;
    private List<Trigger> triggers;


    public TriggerManager() {
        triggers = new ArrayList<>();
    }

    public static TriggerManager getCurrentTriggerManager() {
        return currentTriggerManager;
    }

    public static void setCurrentTriggerManager(TriggerManager currentTriggerManager) {
        TriggerManager.currentTriggerManager = currentTriggerManager;
    }


    public void addTrigger(Trigger trigger) {
        triggers.add(trigger);
    }

    public void removeTrigger(int number) {
        for (Trigger t : triggers)
            if (t.getNumber() == number) {
                triggers.remove(t);
                break;
            }
    }

    public Trigger getTrigger(int number) {
        for (Trigger t : triggers)
            if (t.getNumber() == number) {
                return t;
            }
        throw new RuntimeException("Триггер с указанным номером не найден: " + number);
    }

    public void callAllTriggers() {
        for (Trigger t : triggers)
            if (t.getActive()) {
                t.action();
            }
    }

    public void activateTrigger(int number) {
        for (int i = 0; i < triggers.size(); i++)
            if (triggers.get(i).getNumber() == number)
                triggers.get(i).setActive(true);
    }

    public void deactivateTrigger(int number) {
        for (int i = 0; i < triggers.size(); i++)
            if (triggers.get(i).getNumber() == number)
                triggers.get(i).setActive(false);
    }

}
