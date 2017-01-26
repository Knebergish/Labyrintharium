package ru.temon137.labyrintharium.World.GameObjects.Beings;

import android.graphics.Bitmap;

import ru.temon137.labyrintharium.Function;
import ru.temon137.labyrintharium.World.World;

public class Ghost extends Being {
    private boolean hasStep;

    public Ghost(Bitmap bitmap) {
        super(bitmap);
        hasStep = true;
    }

    @Override
    public void action() {
        if (hasStep)
            move(Function.detectCource(coord, World.getGamer().getCoord()));

        hasStep = !hasStep;
    }

    @Override
    public void receiveDamage(int damage) {

    }

    @Override
    public void death() {

    }
}
