package ru.temon137.labyrintharium;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.Random;

import ru.temon137.labyrintharium.Controls.Control;
import ru.temon137.labyrintharium.World.GameObjects.Beings.Gamer;
import ru.temon137.labyrintharium.World.GameObjects.Beings.Ghost;
import ru.temon137.labyrintharium.World.GameObjects.Blocks.Block;
import ru.temon137.labyrintharium.World.GameObjects.Coord;
import ru.temon137.labyrintharium.World.World;


public class MainGamePanel extends SurfaceView implements SurfaceHolder.Callback {
    MainThread mainThread;

    public MainGamePanel(Context context) {
        super(context);

        getHolder().addCallback(this);

        setFocusable(true);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        World.initialize();

        //Control.init(this);

        mainThread = new MainThread();
        mainThread.setRunning(true);


        loadLevel();


        //World.setRenderThread(new RenderThread(holder));
        World.getRenderThread().setRunning(true);
        World.getRenderThread().start();

        Control.setControlEnabled(true);
        mainThread.start();
    }


    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        //посылаем потоку команду на закрытие и дожидаемся,
        //пока поток не будет закрыт.
        boolean retry = true;
        mainThread.setRunning(false);
        //TODO: УПР
        synchronized (Control.getBalab()) {
            Control.getBalab().notifyAll();
        }
        while (retry) {
            try {
                mainThread.join();
                retry = false;
            } catch (InterruptedException e) {
                // пытаемся снова остановить поток thread
            }
        }

        retry = true;
        // завершаем работу потока
        World.getRenderThread().stopRender();
        while (retry) {
            try {
                World.getRenderThread().join();
                retry = false;
            } catch (InterruptedException e) {
                // если не получилось, то будем пытаться еще и еще
            }
        }


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        /*if (event.getAction() == MotionEvent.ACTION_DOWN) {
            // вызываем метод handleActionDown, куда передаем координаты касания
            //being.handleActionDown((int) event.getX(), (int) event.getY());

            // если щелчок по нижней области экрана, то выходим
            if (event.getY() > getDisplayHeight() - 50) {
                //mainThread.setRunning(false);
                renderThread.setRunning(false);
                ((Activity) getContext()).finish();
            } else {
                Log.d(TAG, "Coords: x=" + event.getX() + ",y=" + event.getY());
            }
        }*/

        if (event.getAction() == MotionEvent.ACTION_DOWN)
            Control.handleEvent(event);

        //Toast.makeText(context, "" + event.getX() + " " + event.getY(), Toast.LENGTH_SHORT).show();


        /*being.handleActionDown((int) event.getX(), (int) event.getY());
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            // перемещение
            if (being.isTouched()) {
                // робот находится в состоянии перетаскивания,
                // поэтому изменяем его координаты
                being.setX((int) event.getX());
                being.setY((int) event.getY());
            }
        }
        if (event.getAction() == MotionEvent.ACTION_UP) {
            // Обрабатываем отпускание
            if (being.isTouched()) {
                being.setTouched(false);
            }
        }*/
        return true;
    }

    private void loadLevel() {
        Gamer gamer = new Gamer(
                BitmapFactory.decodeResource(
                        getResources(),
                        getResources().getIdentifier(
                                getResources().getStringArray(R.array.skins)[Settings.getGamerSkinIndex()],
                                "drawable",
                                "ru.temon137.labyrintharium"
                        )
                )
        );
        gamer.spawn(new Coord(5, 1));
        World.setGamer(gamer);

        Ghost ghost = new Ghost(
                BitmapFactory.decodeResource(
                        getResources(),
                        getResources().getIdentifier(
                                "ghost",
                                "drawable",
                                "ru.temon137.labyrintharium"
                        )
                )
        );
        ghost.spawn(new Coord(2, 1));


        int wallIdentifier = getResources().getIdentifier("lallipop12", "drawable", "ru.temon137.labyrintharium");
        /*new Block(BitmapFactory.decodeResource(getResources(), wallIdentifier), false).spawn(new Coord(5, 4));
        new Block(BitmapFactory.decodeResource(getResources(), wallIdentifier), false).spawn(new Coord(5, 5));
        new Block(BitmapFactory.decodeResource(getResources(), wallIdentifier), false).spawn(new Coord(5, 6));
        new Block(BitmapFactory.decodeResource(getResources(), wallIdentifier), false).spawn(new Coord(5, 7));*/

        for (int x = 0; x < 10; x++)
            new Block(BitmapFactory.decodeResource(getResources(), wallIdentifier), false).spawn(new Coord(x, 0));
        for (int x = 0; x < 10; x++)
            new Block(BitmapFactory.decodeResource(getResources(), wallIdentifier), false).spawn(new Coord(x, 9));
        for (int y = 1; y < 9; y++)
            new Block(BitmapFactory.decodeResource(getResources(), wallIdentifier), false).spawn(new Coord(0, y));
        for (int y = 1; y < 9; y++)
            new Block(BitmapFactory.decodeResource(getResources(), wallIdentifier), false).spawn(new Coord(9, y));

        Random rnd = new Random();
        for (int x = 1; x < 9; x++)
            for (int y = 2; y < 9; y++) {
                if (rnd.nextDouble() < 0.1)
                    new Block(BitmapFactory.decodeResource(getResources(), wallIdentifier), false).spawn(new Coord(x, y));
            }

        int g = getResources().getIdentifier("wooden_floor_2", "drawable", "ru.temon137.labyrintharium");
        for (int x = 0; x < 10; x++)
            for (int y = 0; y < 10; y++)
                new Block(BitmapFactory.decodeResource(getResources(), g), true).spawn(new Coord(x, y));
    }
}
