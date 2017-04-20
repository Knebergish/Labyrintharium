package ru.temon137.labyrintharium.Controls;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.view.MotionEvent;

import ru.temon137.labyrintharium.Settings;
import ru.temon137.labyrintharium.World.GameObjects.Beings.Being;
import ru.temon137.labyrintharium.World.World;


public class StandartController implements IController {
    private boolean leftControl = true;
    private Rect mover;
    private Rect texter;

    private Rect buttonsRect;
    private Rect moveButton;
    private Rect shotButton;
    private Rect useButton;

    private Region leftArrow;
    private Region topArrow;
    private Region rightArrow;
    private Region bottomArrow;

    private Paint moverPaint;
    private Paint arrowPaint;
    private Paint buttonPaint;
    private Paint textPaint;

    private String moveButtonText = "Движение";
    private String shotButtonText = "Выстрел";
    private String useButtonText = "Действие";
    private int moveButtonTextSize;
    private int shotButtonTextSize;
    private int useButtonTextSize;
    private float roundCoeff = 15.0f;
    private int inactiveColor = Color.YELLOW;
    private int activeColor = Color.RED;

    private Action currentAction = Action.MOVE;
    //=============


    public StandartController(Bitmap moveBitmap, Bitmap shotBitmap, Bitmap wallBitmap) {
        Rect leftRect = new Rect(
                0,
                0,
                Settings.getControllerRegionWidth() / 2,
                Settings.getControllerRegionHeight()
        );
        Rect rightRect = new Rect(
                Settings.getControllerRegionWidth() / 2 + 1,
                0,
                Settings.getControllerRegionWidth(),
                Settings.getControllerRegionHeight()
        );

        if (leftControl) {
            mover = new Rect(leftRect);
            texter = new Rect(rightRect);
        } else {
            mover = new Rect(rightRect);
            texter = new Rect(leftRect);
        }

        buttonsRect = new Rect(
                mover.left + mover.width() / 4,
                mover.top + mover.height() / 4,
                mover.right - mover.width() / 4,
                mover.bottom - mover.height() / 4
        );

        moveButton = new Rect(
                buttonsRect.left,
                buttonsRect.top,
                buttonsRect.right,
                buttonsRect.top + buttonsRect.height() / 3
        );
        shotButton = new Rect(
                buttonsRect.left,
                buttonsRect.top + buttonsRect.height() / 3 + 1,
                buttonsRect.right,
                buttonsRect.bottom - buttonsRect.height() / 3
        );
        useButton = new Rect(
                buttonsRect.left,
                buttonsRect.bottom - buttonsRect.height() / 3 + 1,
                buttonsRect.right,
                buttonsRect.bottom
        );


        Rect rect;

        rect = new Rect(
                mover.left,
                buttonsRect.top,
                buttonsRect.left,
                buttonsRect.bottom
        );
        leftArrow = getTriangleRegion(
                rect.right, rect.top,
                rect.right, rect.bottom,
                rect.left, rect.centerY(),
                new Region(rect)
        );

        rect = new Rect(
                buttonsRect.left,
                mover.top,
                buttonsRect.right,
                buttonsRect.top
        );
        topArrow = getTriangleRegion(
                rect.right, rect.bottom,
                rect.left, rect.bottom,
                rect.centerX(), rect.top,
                new Region(rect)
        );

        rect = new Rect(
                buttonsRect.right,
                buttonsRect.top,
                mover.right,
                buttonsRect.bottom
        );
        rightArrow = getTriangleRegion(
                rect.left, rect.top,
                rect.left, rect.bottom,
                rect.right, rect.centerY(),
                new Region(rect)
        );

        rect = new Rect(
                buttonsRect.left,
                buttonsRect.bottom,
                buttonsRect.right,
                mover.bottom
        );
        bottomArrow = getTriangleRegion(
                rect.left, rect.top,
                rect.right, rect.top,
                rect.centerX(), rect.bottom,
                new Region(rect)
        );


        moverPaint = new Paint();
        moverPaint.setColor(Color.GRAY);
        moverPaint.setStrokeWidth(3);
        moverPaint.setFilterBitmap(false);
        moverPaint.setAntiAlias(false);

        arrowPaint = new Paint();
        arrowPaint.setColor(Color.RED);

        buttonPaint = new Paint();
        buttonPaint.setStrokeWidth(3);
        buttonPaint.setColor(Color.YELLOW);

        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setStyle(Paint.Style.STROKE);

        moveButtonTextSize = getTextSize(moveButton, moveButtonText);
        shotButtonTextSize = getTextSize(shotButton, shotButtonText);
        useButtonTextSize = getTextSize(useButton, useButtonText);
    }

    @Override
    public synchronized void handleEvent(MotionEvent event) {
        if (changeAction(event))
            return;

        Being.Cource cource = getCource(event);
        if (cource == Being.Cource.None)
            return;

        switch (currentAction) {
            case MOVE: {
                World.getGamer().move(cource);
                break;
            }

            case SHOT: {
                World.getGamer().shot(cource);
                break;
            }

            case USE: {

                break;
            }
        }

    }

    @Override
    public void render(Canvas canvas) {
        canvas.drawColor(Color.DKGRAY);

        canvas.drawRect(mover, moverPaint);

        //canvas.drawRect(buttonsRect, shotterPaint);

        setColor(Action.MOVE, buttonPaint);
        canvas.drawRoundRect(new RectF(moveButton), roundCoeff, roundCoeff, buttonPaint);
        textPaint.setTextSize(moveButtonTextSize);
        canvas.drawText(moveButtonText,
                        moveButton.left,
                        moveButton.centerY() + moveButtonTextSize / 3,
                        textPaint);

        setColor(Action.SHOT, buttonPaint);
        canvas.drawRoundRect(new RectF(shotButton), roundCoeff, roundCoeff, buttonPaint);
        textPaint.setTextSize(shotButtonTextSize);
        canvas.drawText(shotButtonText,
                        shotButton.left,
                        shotButton.centerY() + shotButtonTextSize / 3,
                        textPaint);

        setColor(Action.USE, buttonPaint);
        canvas.drawRoundRect(new RectF(useButton), roundCoeff, roundCoeff, buttonPaint);
        textPaint.setTextSize(useButtonTextSize);
        canvas.drawText(useButtonText,
                        useButton.left,
                        useButton.centerY() + useButtonTextSize / 3,
                        textPaint);


        canvas.drawPath(rightArrow.getBoundaryPath(), arrowPaint);
        canvas.drawPath(leftArrow.getBoundaryPath(), arrowPaint);
        canvas.drawPath(topArrow.getBoundaryPath(), arrowPaint);
        canvas.drawPath(bottomArrow.getBoundaryPath(), arrowPaint);
    }

    private Region getTriangleRegion(int x1, int y1,
                                     int x2, int y2,
                                     int x3, int y3,
                                     Region clip) {
        Point a = new Point(x1, y1);
        Point b = new Point(x2, y2);
        Point c = new Point(x3, y3);

        Path path = new Path();
        path.setFillType(Path.FillType.EVEN_ODD);
        path.moveTo(b.x, b.y);
        path.lineTo(b.x, b.y);
        path.lineTo(c.x, c.y);
        path.lineTo(a.x, a.y);
        path.close();

        Region triangleRegion = new Region();
        triangleRegion.setPath(path, clip);

        return triangleRegion;
    }

    private int getTextSize(Rect rect, String text) {
        Paint fontPaint;
        int fontSize = rect.height();

        fontPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        fontPaint.setStyle(Paint.Style.STROKE);
        fontPaint.setTextSize(fontSize);

        while (fontPaint.measureText(text) > rect.width()) {
            fontPaint.setTextSize(--fontSize);
        }

        return fontSize;
    }

    private boolean changeAction(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();

        if (moveButton.contains(x, y)) {
            currentAction = Action.MOVE;
        } else if (shotButton.contains(x, y)) {
            currentAction = Action.SHOT;
        } else if (useButton.contains(x, y)) {
            currentAction = Action.USE;
        } else {
            return false;
        }

        return true;
    }

    private Being.Cource getCource(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();

        if (leftArrow.contains(x, y)) {
            return Being.Cource.Left;
        } else if (topArrow.contains(x, y)) {
            return Being.Cource.Up;
        } else if (rightArrow.contains(x, y)) {
            return Being.Cource.Right;
        } else if (bottomArrow.contains(x, y)) {
            return Being.Cource.Down;
        } else {
            return Being.Cource.None;
        }
    }

    private void move(MotionEvent event) {
        World.getGamer().move(getCource(event));
    }

    private void shot(MotionEvent event) {
        World.getGamer().shot(getCource(event));
    }

    private void setColor(Action action, Paint paint) {
        if (currentAction == action) {
            paint.setColor(activeColor);
        } else {
            paint.setColor(inactiveColor);
        }
    }
}

enum Action {
    MOVE,
    SHOT,
    USE
}
