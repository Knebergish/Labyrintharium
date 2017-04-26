package ru.temon137.labyrintharium.Activities;


import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import ru.temon137.labyrintharium.Controls.Control;
import ru.temon137.labyrintharium.Controls.StandartController;
import ru.temon137.labyrintharium.MainThread;
import ru.temon137.labyrintharium.R;
import ru.temon137.labyrintharium.Render.ControllerSurfaceView;
import ru.temon137.labyrintharium.Render.MainSurfaceView;
import ru.temon137.labyrintharium.Settings;
import ru.temon137.labyrintharium.World.GameObjects.Beings.Gamer;
import ru.temon137.labyrintharium.World.GameObjects.Beings.Ghost;
import ru.temon137.labyrintharium.World.GameObjects.Blocks.Background;
import ru.temon137.labyrintharium.World.GameObjects.Blocks.Block;
import ru.temon137.labyrintharium.World.GameObjects.Blocks.Gold;
import ru.temon137.labyrintharium.World.GameObjects.Coord;
import ru.temon137.labyrintharium.World.Trigger;
import ru.temon137.labyrintharium.World.TriggerManager;
import ru.temon137.labyrintharium.World.World;


public class GameActivity extends AppCompatActivity {
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
        String wall = "lallipop12";
        int wallId = getResources().getIdentifier(wall, "drawable", "ru.temon137.labyrintharium");


        for (int x = 0; x < 10; x++)
            new Block(BitmapFactory.decodeResource(getResources(), wallId), false).spawn(
                    new Coord(x, 0));
        for (int x = 0; x < 10; x++)
            new Block(BitmapFactory.decodeResource(getResources(), wallId), false).spawn(
                    new Coord(x, 9));
        for (int y = 1; y < 9; y++)
            new Block(BitmapFactory.decodeResource(getResources(), wallId), false).spawn(
                    new Coord(0, y));
        for (int y = 1; y < 9; y++)
            new Block(BitmapFactory.decodeResource(getResources(), wallId), false).spawn(
                    new Coord(9, y));


        int floorId = getResources().getIdentifier("wooden_floor_2", "drawable", "ru.temon137.labyrintharium");
        for (int x = 0; x < 10; x++)
            for (int y = 0; y < 10; y++)
                new Background(BitmapFactory.decodeResource(getResources(), floorId), true).spawn(
                        new Coord(x, y));


        setBlock(wall, 6, 1, false);
        setBlock(wall, 6, 2, false);
        setBlock(wall, 6, 4, false);
        setBlock(wall, 5, 4, false);
        setBlock(wall, 4, 4, false);
        setBlock(wall, 3, 4, false);
        setBlock(wall, 2, 4, false);
        setBlock(wall, 1, 4, false);
        setBlock("torch_3", 5, 2, true);
        setBlock("bunk_bed", 1, 1, false);
        setBlock("table_with_books_1", 3, 2, false);
        setBlock("closed_doors_2", 6, 3, false);
        setBlock("bookshelves", 5, 1, false);
        setBlock("warrior_statue", 7, 4, false);
        setBlock("wizard_statue", 4, 8, false);
        World.getBackgroundsMap().removeT(new Coord(4, 1));
        new Background(BitmapFactory.decodeResource(getResources(), wallId), false).spawn(new Coord(4, 1));
        setBlock("picture_2", 4, 1, true);
        setBlock(wall, 3, 5, false);
        setBlock(wall, 3, 6, false);
        setBlock(wall, 3, 7, false);
        setBlock(wall, 3, 8, false);
        setBlock(wall, 2, 5, false);
        setBlock(wall, 2, 6, false);
        setBlock(wall, 2, 7, false);
        setBlock(wall, 2, 8, false);
        setBlock(wall, 1, 5, false);
        setBlock(wall, 1, 6, false);
        setBlock(wall, 1, 7, false);
        setBlock(wall, 1, 8, false);

        int gold = getResources().getIdentifier("gold", "drawable", "ru.temon137.labyrintharium");
        new Gold(BitmapFactory.decodeResource(getResources(), gold), true).spawn(new Coord(1, 3));

        Gamer gamer = new Gamer(
                BitmapFactory.decodeResource(
                        getResources(),
                        getResources().getIdentifier(getResources().getStringArray(
                                R.array.skinsFiles)[Settings.getPlayerSkinIndex()],
                                                     "drawable",
                                                     "ru.temon137.labyrintharium"
                        )
                ),
                BitmapFactory.decodeResource(
                        getResources(),
                        getResources().getIdentifier(
                                "magik",
                                "drawable",
                                "ru.temon137.labyrintharium"
                        )
                )
        );
        gamer.spawn(new Coord(8, 1));
        World.setGamer(gamer);


        TriggerManager triggerManager = new TriggerManager();
        TriggerManager.setCurrentTriggerManager(triggerManager);
        triggerManager.addTrigger(new Trigger(0, true) {
            @Override
            public void action() {
                if (World.getGamer().getCoord().getY() == 3 &&
                        World.getGamer().getCoord().getX() == 6) {
                    StandartController currentController = Control.getCurrentController();

                    currentController.addLog("Перед вами комната, в которой, судя по слою пыли, уже давно не живут.");
                    currentController.addFutureLog("Странно, но факел на стене горит.");
                    currentController.addFutureLog("Вам стоит обыскать комнату, в ней могут быть ценные вещи.");

                    TriggerManager.getCurrentTriggerManager().deactivateTrigger(0);
                }
            }
        });
        triggerManager.addTrigger(new Trigger(1, true) {
            @Override
            public void action() {
                if (World.getGamer().getCoord().getY() == 5) {
                    StandartController currentController = Control.getCurrentController();

                    currentController.addLog("В дальнем углу комнаты стоит статуя мага.");
                    currentController.addFutureLog("Стоит ли к ней подходить?..");

                    TriggerManager.getCurrentTriggerManager().deactivateTrigger(1);
                }
            }
        });
        triggerManager.addTrigger(new Trigger(2, true) {
            @Override
            public void action() {
                if (World.getGamer().getCoord().getY() >= 5 &&
                        World.getGamer().getCoord().getX() == 6) {
                    StandartController currentController = Control.getCurrentController();

                    new Ghost(BitmapFactory.decodeResource(
                            getResources(),
                            getResources().getIdentifier(
                                    "ghost",
                                    "drawable",
                                    "ru.temon137.labyrintharium"
                            )
                    )).spawn(new Coord(5, 8));

                    currentController.addLog("Неожиданно из статуи вылетает призрачный силуэт.");
                    currentController.addFutureLog("Во всё время расплывающемся лице призрака вы можете узнать лицо статуи.");
                    currentController.addFutureLog("Призрак направляется к вам, издавая странные завывающие звуки.");

                    TriggerManager.getCurrentTriggerManager().deactivateTrigger(2);
                }
            }
        });
        triggerManager.addTrigger(new Trigger(3, true) {
            @Override
            public void action() {
                if (Math.abs(World.getGamer().getCoord().getY() - 3) <= 1 &&
                        Math.abs(World.getGamer().getCoord().getX() - 6) <= 1) {
                    World.getBlocksMap().removeT(new Coord(6, 3));
                    setBlock("open_doors_2_1", 6, 3, true);

                    TriggerManager.getCurrentTriggerManager().deactivateTrigger(3);
                    TriggerManager.getCurrentTriggerManager().activateTrigger(4);
                }
            }
        });
        triggerManager.addTrigger(new Trigger(4, false) {
            @Override
            public void action() {
                if (Math.abs(World.getGamer().getCoord().getY() - 3) > 1 ||
                        Math.abs(World.getGamer().getCoord().getX() - 6) > 1) {
                    World.getBlocksMap().removeT(new Coord(6, 3));
                    setBlock("closed_doors_2", 6, 3, false);

                    TriggerManager.getCurrentTriggerManager().activateTrigger(3);
                    TriggerManager.getCurrentTriggerManager().deactivateTrigger(4);
                }
            }
        });
    }

    private void setBlock(String name, int x, int y, boolean passableness) {
        int blockId = getResources().getIdentifier(name, "drawable", "ru.temon137.labyrintharium");
        new Block(BitmapFactory.decodeResource(getResources(), blockId), passableness).spawn(new Coord(x, y));
    }
}
