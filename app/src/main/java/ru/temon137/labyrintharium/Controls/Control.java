package ru.temon137.labyrintharium.Controls;

import android.view.MotionEvent;

import ru.temon137.labyrintharium.Settings;

public class Control {
    private static boolean controlEnabled;
    private static final Object balab = new Object();

    private static IController controller;

    public static void init() {
        controlEnabled = false;
        controller = null;

        controller = new StandartController();
    }

    public static void handleEvent(MotionEvent event) {
        if (!controlEnabled)
            return;

        if (event.getY() > Settings.getMainRegionLength()) {
            event.setLocation(event.getX(), event.getY() - Settings.getMainRegionLength());
            if (controller != null)
                controller.handleEvent(event);
        }


        synchronized (balab) {
            balab.notifyAll();
        }
    }

    public static Object getBalab() {
        return balab;
    }

    public static boolean isControlEnabled() {
        return controlEnabled;
    }

    public static void setControlEnabled(boolean controlEnabled) {
        Control.controlEnabled = controlEnabled;
    }

    public static IController getController() {
        return controller;
    }

    public static void setController(IController controller) {
        Control.controller = controller;
    }
}
