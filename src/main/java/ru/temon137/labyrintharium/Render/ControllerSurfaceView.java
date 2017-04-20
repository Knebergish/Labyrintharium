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

        int moveIdentifier = getResources().getIdentifier("mover", "drawable", "ru.temon137.labyrintharium");
        Bitmap moveBitmap = BitmapFactory.decodeResource(getResources(), moveIdentifier);

        int shotIdentifier = getResources().getIdentifier("spell_button", "drawable", "ru.temon137.labyrintharium");
        Bitmap shotBitmap = BitmapFactory.decodeResource(getResources(), shotIdentifier);

        int wallIdentifier = getResources().getIdentifier("wooden_floor", "drawable", "ru.temon137.labyrintharium");
        Bitmap wallBitmap = BitmapFactory.decodeResource(getResources(), wallIdentifier);

        StandartController standartController = new StandartController(moveBitmap, shotBitmap, wallBitmap);
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
