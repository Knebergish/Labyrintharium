package ru.temon137.labyrintharium.Controls;


import android.view.MotionEvent;

import ru.temon137.labyrintharium.Render.IRenderable;


public interface IController extends IRenderable{
    void handleEvent(MotionEvent event);
}
