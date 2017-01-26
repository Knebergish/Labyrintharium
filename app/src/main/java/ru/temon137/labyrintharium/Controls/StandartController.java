package ru.temon137.labyrintharium.Controls;


import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

import ru.temon137.labyrintharium.Settings;
import ru.temon137.labyrintharium.World.GameObjects.Beings.Being;
import ru.temon137.labyrintharium.World.World;


public class StandartController implements IController {
    private Paint paint;

    public StandartController() {
        paint = new Paint();
        paint.setColor(Color.GREEN);
        paint.setStrokeWidth(1);
        paint.setFilterBitmap(false);
        paint.setAntiAlias(false);
    }

    @Override
    public void render(Canvas canvas) {
        canvas.drawColor(Color.WHITE);
        canvas.drawLine(0, 0, canvas.getWidth(), canvas.getHeight(), paint);
        canvas.drawLine(canvas.getWidth(), 0, 0, canvas.getHeight(), paint);
    }

    @Override
    public synchronized void handleEvent(MotionEvent event) {
        int maxX = Settings.getControllerRegionWidth();
        int maxY = Settings.getControllerRegionHeight();
        float coeff = maxX / maxY;

        float x = event.getX();
        float y = event.getY();

        if (x > y * coeff && x < maxY - y * coeff)
            World.getGamer().move(Being.Cource.Up);
        if (x <= y * coeff && x >= maxY - y * coeff)
            World.getGamer().move(Being.Cource.Down);
        if (x > y * coeff && x >= maxY - y * coeff)
            World.getGamer().move(Being.Cource.Right);
        if (x <= y * coeff && x < maxY - y * coeff)
            World.getGamer().move(Being.Cource.Left);
    }
}
