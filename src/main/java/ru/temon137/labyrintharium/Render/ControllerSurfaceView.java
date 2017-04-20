package ru.temon137.labyrintharium.Render;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import ru.temon137.labyrintharium.Controls.Control;
import ru.temon137.labyrintharium.Controls.StandartController;


public class ControllerSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    public ControllerSurfaceView(Context context) {
        super(context);
        getHolder().addCallback(this);

        Control.init();

        int wallIdentifier = getResources().getIdentifier("lallipop12", "drawable", "ru.temon137.labyrintharium");
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), wallIdentifier);

        StandartController standartController = new StandartController(bitmap);
        Control.addController(standartController);
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
