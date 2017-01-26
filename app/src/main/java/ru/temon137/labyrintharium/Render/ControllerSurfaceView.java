package ru.temon137.labyrintharium.Render;


import android.content.Context;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import ru.temon137.labyrintharium.Controls.Control;
import ru.temon137.labyrintharium.Controls.StandartController;
import ru.temon137.labyrintharium.World.World;


public class ControllerSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    public ControllerSurfaceView(Context context) {
        super(context);
        getHolder().addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        Control.init();
        StandartController standartController = new StandartController(this);
        Control.setController(standartController);
        World.getRenderThread().addRendereable(standartController);
        Control.setControlEnabled(true);
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }
}
