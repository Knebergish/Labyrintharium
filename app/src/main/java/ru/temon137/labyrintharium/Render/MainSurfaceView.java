package ru.temon137.labyrintharium.Render;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;

import ru.temon137.labyrintharium.Settings;
import ru.temon137.labyrintharium.World.GameObjects.Coord;
import ru.temon137.labyrintharium.World.GameObjects.GameObject;
import ru.temon137.labyrintharium.World.World;


public class MainSurfaceView extends SurfaceView implements SurfaceHolder.Callback, IRenderable {
    private int fps;
    private long previousTime;

    private float coeff;

    private Paint paint;
    //=============


    public MainSurfaceView(Context context) {
        super(context);
        getHolder().addCallback(this);

        fps = 0;
        previousTime = System.currentTimeMillis();

        paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(1);
        paint.setFilterBitmap(false);
        paint.setAntiAlias(false);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        World.getRenderThread().addRendereable(this);
        World.getRenderThread().startRender();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }

    @Override
    public SurfaceHolder getSurfaceHolder() {
        return getHolder();
    }

    @Override
    public void render(Canvas canvas) {
        coeff = canvas.getWidth() / Settings.getCountCellInScreen();

        nextFPS();
        drawScreen(canvas);
        canvas.drawText(Integer.toString(fps), 50, 50, paint);
    }


    private void nextFPS() {
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
                canvas.getWidth(),
                canvas.getHeight(),
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
                xCoord * coeff,
                yCoord * coeff,
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
