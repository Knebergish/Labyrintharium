package ru.temon137.labyrintharium.Render;


import android.graphics.Canvas;
import android.view.SurfaceHolder;


public interface IRenderable {
    SurfaceHolder getSurfaceHolder();

    void render(Canvas canvas);
}
