package ru.temon137.labyrintharium.Activities;


import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import java.util.Random;

import ru.temon137.labyrintharium.Controls.Control;
import ru.temon137.labyrintharium.Controls.StandartController;
import ru.temon137.labyrintharium.MainThread;
import ru.temon137.labyrintharium.R;
import ru.temon137.labyrintharium.Render.ControllerSurfaceView;
import ru.temon137.labyrintharium.Render.MainSurfaceView;
import ru.temon137.labyrintharium.Settings;
import ru.temon137.labyrintharium.World.GameObjects.Beings.Gamer;
import ru.temon137.labyrintharium.World.GameObjects.Beings.Ghost;
import ru.temon137.labyrintharium.World.GameObjects.Blocks.Block;
import ru.temon137.labyrintharium.World.GameObjects.Coord;
import ru.temon137.labyrintharium.World.World;


public class GameActivity extends AppCompatActivity {
    private boolean isInit = false;
    MainSurfaceView mainSurfaceView;
    ControllerSurfaceView controllerSurfaceView;
    //============


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        setContentView(R.layout.activity_game);


        mainSurfaceView = new MainSurfaceView(this);
        controllerSurfaceView = new ControllerSurfaceView(this);

        mainSurfaceView.setLayoutParams(new LinearLayout.LayoutParams(
                Settings.getMainRegionLength(),
                Settings.getMainRegionLength()
        ));
        controllerSurfaceView.setLayoutParams(new LinearLayout.LayoutParams(
                Settings.getControllerRegionWidth(),
                Settings.getControllerRegionHeight()
        ));


        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        layout.addView(mainSurfaceView);
        layout.addView(controllerSurfaceView);

        addContentView(
                layout,
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT
                )
        );
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        World.getRenderThread().setMainSurfaceView(mainSurfaceView);
        World.getRenderThread().setControllerSurfaceView(controllerSurfaceView);

        loadLevel();

        MainThread mainThread = new MainThread();
        mainThread.setRunning(true);

        mainThread.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN)
            Control.handleEvent(event);

        return true;
    }


    @Override
    protected void onDestroy() {
        Log.d("Тут", "Destroying...");
        super.onDestroy();
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
