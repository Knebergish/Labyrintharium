package ru.temon137.labyrintharium.Controls;


import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

import ru.temon137.labyrintharium.Render.IRenderable;
import ru.temon137.labyrintharium.Settings;
import ru.temon137.labyrintharium.World.GameObjects.Beings.Being;
import ru.temon137.labyrintharium.World.GameObjects.Beings.Gamer;
import ru.temon137.labyrintharium.World.World;


public class StandartController implements IController, IRenderable {
    private Paint paint;
    private Paint shotterPaint;

    private int shifterWidth;
    private int shifterHeight;
    private int shotterWidth;
    private int shotterHeight;
    private float coeff;
    private boolean isShot;
    //=============


    public StandartController() {
        paint = new Paint();
        paint.setColor(Color.GREEN);
        paint.setStrokeWidth(3);
        paint.setFilterBitmap(false);
        paint.setAntiAlias(false);

        shotterPaint = new Paint();
        shotterPaint.setColor(Color.RED);

        shifterWidth = Settings.getControllerRegionWidth() / 2;
        shifterHeight = Settings.getControllerRegionHeight();
        shotterWidth = Settings.getControllerRegionWidth() / 2;
        shotterHeight = Settings.getControllerRegionHeight();
        coeff = (float) shifterWidth / (float) shifterHeight;

        isShot = false;
    }

    @Override
    public synchronized void handleEvent(MotionEvent event) {
        if (event.getX() <= shifterWidth && event.getY() <= shifterHeight)
            shift(event);
        else
            swapShot();
    }

    @Override
    public void render(Canvas canvas) {
        canvas.drawColor(Color.WHITE);
        canvas.drawLine(0, 0, shifterWidth, shifterHeight, paint);
        canvas.drawLine(shifterWidth, 0, 0, shifterHeight, paint);
        canvas.drawRect(shifterWidth + 1, 0, shifterWidth + shotterWidth, shotterHeight, shotterPaint);
    }


    private void shift(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        float y1 = y * coeff;
        float y2 = shifterWidth - y1;

        Being.Cource cource = Being.Cource.None;

        if (x <= y1 && x < y2)
            cource = Being.Cource.Left;
        if (x > y1 && x < y2)
            cource = Being.Cource.Up;
        if (x > y1 && x >= y2)
            cource = Being.Cource.Right;
        if (x <= y1 && x >= y2)
            cource = Being.Cource.Down;

        if (!isShot)
            World.getGamer().move(cource);
        else
            ((Gamer) World.getGamer()).shot(cource);
    }

    private void swapShot() {
        isShot = !isShot;
        if (isShot)
            shotterPaint.setColor(Color.GREEN);
        else
            shotterPaint.setColor(Color.RED);
    }
}
