package ru.temon137.labyrintharium.World.GameObjects;


import android.graphics.Bitmap;

import ru.temon137.labyrintharium.ManualResetEvent;
import ru.temon137.labyrintharium.World.GameObjects.Beings.Being;
import ru.temon137.labyrintharium.World.GameObjects.Blocks.Block;
import ru.temon137.labyrintharium.World.GameObjects.Blocks.Gold;
import ru.temon137.labyrintharium.World.World;


public class Spell extends GameObject {
    private ManualResetEvent despawn;
    private boolean isSpawned;
    private int range;
    private int currentRange;
    private Being.Cource cource;
    private long pause;
    private long prevTime;


    public Spell(Bitmap bitmap, int range, long pause) {
        renderComponent = new SingleRenderComponent(bitmap);
        isSpawned = false;
        despawn = new ManualResetEvent(!isSpawned);
        this.range = range;
        currentRange = 0;
        this.pause = pause;
    }

    public void shot(Being.Cource cource) {
        this.cource = cource;
        this.prevTime = System.currentTimeMillis();
        despawn.reset();
        isSpawned = true;
    }

    public void update() {
        if (System.currentTimeMillis() < prevTime + pause)
            return;

        prevTime = System.currentTimeMillis();

        if (move(cource)) {
            currentRange++;
        } else
            isSpawned = false;

        if (currentRange == range)
            isSpawned = false;

        if (!isSpawned)
            despawn.set();
    }

    @Override
    protected boolean isValidPosition(Coord newCoord) {
        boolean result = true;
        Block block;

        block = World.getBackgroundsMap().getT(newCoord);
        if (block != null)
            result = result & block.getPassableness();

        block = World.getBlocksMap().getT(newCoord);
        if (block != null)
            result = result & block.getPassableness();

        return result;
    }

    @Override
    protected void changeCoordHandler() {
        Being being = World.getBeingsMap().getT(coord);
        if (being != null) {
            being.receiveDamage(1);
            despawn();
        }

        Block block = World.getBlocksMap().getT(coord);
        if (block instanceof Gold) {
            block.despawn();
            despawn();
        }
    }

    @Override
    public boolean spawn(Coord coord) {
        this.coord = coord;
        currentRange = 0;
        return true;
    }

    @Override
    public void despawn() {
        isSpawned = false;
    }

    public boolean move(Being.Cource cource) {
        int deltaX = 0;
        int deltaY = 0;

        switch (cource) {
            case Left:
                deltaX--;
                break;
            case Up:
                deltaY--;
                break;
            case Right:
                deltaX++;
                break;
            case Down:
                deltaY++;
                break;
            case None:
                return true;
        }

        return setCoord(new Coord(coord.getX() + deltaX, coord.getY() + deltaY));
    }

    public ManualResetEvent getDespawn() {
        return despawn;
    }

    public boolean isSpawned() {
        return isSpawned;
    }
}
