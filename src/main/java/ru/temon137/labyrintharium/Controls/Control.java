package ru.temon137.labyrintharium.Controls;


import android.view.MotionEvent;

import java.util.Stack;

import ru.temon137.labyrintharium.Settings;
import ru.temon137.labyrintharium.World.World;


public class Control {
    private static boolean controlEnabled;

    private static Stack<IController> controllers;

    public static void init() {
        controlEnabled = false;
        controllers = new Stack<>();
    }

    public static void handleEvent(MotionEvent event) {
        if (!controlEnabled)
            return;

        if (event.getY() > Settings.getMainRegionLength()) {
            event.setLocation(event.getX(), event.getY() - Settings.getMainRegionLength());
            if (controllers.size() > 0)
                controllers.peek().handleEvent(event);
        }
    }

    public static boolean isControlEnabled() {
        return controlEnabled;
    }

    public static void setControlEnabled(boolean controlEnabled) {
        Control.controlEnabled = controlEnabled;
    }

    public static IController getCurrentController() {
        return controllers.peek();
    }

    public static void addController(IController controller) {
        controllers.push(controller);
        World.getRenderThread().setControllerRenderer(controller);
    }

    public static void removeController() {
        controllers.pop();
        World.getRenderThread().setControllerRenderer(getCurrentController());
    }
}
