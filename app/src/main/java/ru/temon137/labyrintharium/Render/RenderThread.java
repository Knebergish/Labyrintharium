package ru.temon137.labyrintharium.Render;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;

import java.util.ArrayList;
import java.util.List;

import ru.temon137.labyrintharium.Controls.Control;
import ru.temon137.labyrintharium.Controls.IController;
import ru.temon137.labyrintharium.Settings;
import ru.temon137.labyrintharium.World.GameObjects.Coord;
import ru.temon137.labyrintharium.World.GameObjects.GameObject;
import ru.temon137.labyrintharium.World.World;

public class RenderThread extends Thread {
    private boolean runFlag;
    private boolean renderFlag;

    private int fps;
    private long previousTime;

    private final SurfaceHolder mainSurfaceHolder;
    private final SurfaceHolder controllerSurfaceHolder;

    private Paint paint;

    private IController controller;
    //=============


    public RenderThread(SurfaceHolder mainSurfaceHolder, SurfaceHolder controllerSurfaceHolder) {
        runFlag = true;
        renderFlag = false;

        fps = 0;
        previousTime = System.currentTimeMillis();

        this.mainSurfaceHolder = mainSurfaceHolder;
        this.controllerSurfaceHolder = controllerSurfaceHolder;

        paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(1);
        paint.setFilterBitmap(false);
        paint.setAntiAlias(false);

        controller = null;
    }

    public void setRunning(boolean run) {
        renderFlag = run;
    }

    public void stopRender() {
        runFlag = false;
    }

    public void setController(IController controller) {
        this.controller = controller;
    }

    @Override
    public void run() {

        while (runFlag) {
            if (!renderFlag)
                //TODO: Ух ужас, реализовать нормальное ожидание
                continue;


            nextFPS();

            render();
        }
    }

    private void render() {
        Canvas mainCanvas;
        Canvas controllerCanvas;

        synchronized (mainSurfaceHolder) {
            mainCanvas = mainSurfaceHolder.lockCanvas(null);

            drawScreen(mainCanvas);

            mainCanvas.drawText(Integer.toString(fps), 50, 50, paint);

            mainSurfaceHolder.unlockCanvasAndPost(mainCanvas);
        }

        if (Control.getController() != null) {
            synchronized (controllerSurfaceHolder) {
                controllerCanvas = controllerSurfaceHolder.lockCanvas(null);

                Control.getController().render(controllerCanvas);

                controllerSurfaceHolder.unlockCanvasAndPost(controllerCanvas);
            }
        }
    }


    private void nextFPS() {
        if (!renderFlag) {
            fps = 0;
            return;
        }

        long nowTime = System.currentTimeMillis();
        long elapsedTime = nowTime - previousTime;
        elapsedTime = elapsedTime == 0 ? 1 : elapsedTime;

        previousTime = nowTime;
        fps = 1000 / (int) elapsedTime;
    }


    private void drawScreen(Canvas canvas) {
        // Отрисовка стандартного фона
        canvas.drawColor(Color.WHITE);

        canvas.drawRect(
                0,
                0,
                Settings.getMainRegionLength(),
                Settings.getMainRegionLength(),
                new Paint(Color.BLACK)
        );

        List<Cell> cells = new ArrayList<>();

        // Отрисовка фонов, находящихся в камере.
        for (GameObject gameObject : World.getBackgroundsMap().getAllT()) {
            cells.add(new Cell(gameObject.getCoord(), gameObject.getRenderComponent().getBitmap()));
        }
        // Отрисовка блоков, находящихся в камере.
        for (GameObject gameObject : World.getBlocksMap().getAllT()) {
            cells.add(new Cell(gameObject.getCoord(), gameObject.getRenderComponent().getBitmap()));
        }
        // Отрисовка сущностей, находящихся в камере.
        for (GameObject gameObject : World.getBeingsMap().getAllT()) {
            cells.add(new Cell(gameObject.getCoord(), gameObject.getRenderComponent().getBitmap()));
        }

        Coord gamerCoord = World.getGamer().getCoord();

        for (Cell cell : cells)
            drawOrNotDraw(canvas, gamerCoord, cell);
    }

    private void drawOrNotDraw(Canvas canvas, Coord observerCoord, Cell cell) {
        int maxDelta = Settings.getCountCellInScreen() / 2;
        int deltaX = cell.getCoord().getX() - observerCoord.getX();
        int deltaY = cell.getCoord().getY() - observerCoord.getY();

        if (Math.abs(deltaX) <= maxDelta && Math.abs(deltaY) <= maxDelta)
            drawBitmap(
                    canvas,
                    cell.getBitmap(),
                    deltaX + Settings.getCountCellInScreen() / 2,
                    deltaY + Settings.getCountCellInScreen() / 2
            );
    }

    private void drawBitmap(Canvas canvas, Bitmap bitmap, int xCoord, int yCoord) {
        canvas.drawBitmap(
                bitmap,
                xCoord * Settings.getCoeff(),
                yCoord * Settings.getCoeff(),
                null
        );
    }


    private class Cell {
        private Coord coord;
        private Bitmap bitmap;

        public Cell(Coord coord, Bitmap bitmap) {
            this.coord = coord;
            this.bitmap = bitmap;
        }

        public Coord getCoord() {
            return coord;
        }

        public Bitmap getBitmap() {
            return bitmap;
        }
    }
}



