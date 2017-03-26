package ru.temon137.labyrintharium.World.GameObjects.Blocks;

import android.graphics.Bitmap;

import ru.temon137.labyrintharium.World.GameObjects.Coord;
import ru.temon137.labyrintharium.World.World;

public class Background extends Block {
    public Background(Bitmap bitmap, boolean passableness) {
        super(bitmap, passableness);
    }

    @Override
    public boolean spawn(Coord coord) {
        return setCoord(coord) && World.getBackgroundsMap().addT(this);
    }

    @Override
    public void despawn() {
        World.getBackgroundsMap().removeT(coord);
    }
}
