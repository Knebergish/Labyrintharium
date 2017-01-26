package ru.temon137.labyrintharium.Controls;


import android.graphics.Canvas;
import android.view.MotionEvent;


public interface IController {
    void render(Canvas canvas);

    void handleEvent(MotionEvent event);
}
