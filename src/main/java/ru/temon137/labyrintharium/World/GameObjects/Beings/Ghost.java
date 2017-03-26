package ru.temon137.labyrintharium.World.GameObjects.Beings;

import android.graphics.Bitmap;

import ru.temon137.labyrintharium.Function;
import ru.temon137.labyrintharium.World.World;

public class Ghost extends Being {
    private boolean hasStep;
    private int hp;

    public Ghost(Bitmap bitmap) {
        super(bitmap);
        hasStep = true;
        hp = 5;
    }

    @Override
    public void action() {
        if (hasStep)
            move(Function.detectCource(coord, World.getGamer().getCoord()));

        hasStep = !hasStep;
    }

    @Override
    public void receiveDamage(int damage) {
        hp--;
        if (hp <= 0) {
            despawn();
        }
    }

    @Override
    public void death() {

    }
}
