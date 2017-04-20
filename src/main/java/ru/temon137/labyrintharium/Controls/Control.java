package ru.temon137.labyrintharium.Controls;


import android.view.MotionEvent;

import ru.temon137.labyrintharium.Settings;
import ru.temon137.labyrintharium.World.World;


public class Control {
    private static boolean controlEnabled;

    private static StandartController controller;

    public static void init() {
        controlEnabled = false;
    }

    public static void handleEvent(MotionEvent event) {
        if (!controlEnabled)
            return;

        if (event.getY() > Settings.getMainRegionLength()) {
            event.setLocation(event.getX(), event.getY() - Settings.getMainRegionLength());
            if (controller != null)
                controller.handleEvent(event);
        }
    }

    public static boolean isControlEnabled() {
        return controlEnabled;
    }

    public static void setControlEnabled(boolean controlEnabled) {
        Control.controlEnabled = controlEnabled;
    }

    public static StandartController getCurrentController() {
        return controller;
    }

    public static void setController(StandartController controller) {
        Control.controller = controller;
        World.getRenderThread().setControllerRenderer(controller);
    }
}
