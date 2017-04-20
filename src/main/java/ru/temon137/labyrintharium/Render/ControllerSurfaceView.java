package ru.temon137.labyrintharium.Render;


import android.content.Context;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import ru.temon137.labyrintharium.Controls.Control;
import ru.temon137.labyrintharium.Controls.StandartController;
import ru.temon137.labyrintharium.Settings;


public class ControllerSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    public ControllerSurfaceView(Context context) {
        super(context);
        getHolder().addCallback(this);

        Control.init();
        StandartController standartController = new StandartController(
                Settings.getControlPosition());

        Control.setController(standartController);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        Control.setControlEnabled(true);
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }
}
