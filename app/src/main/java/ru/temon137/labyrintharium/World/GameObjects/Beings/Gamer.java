package ru.temon137.labyrintharium.World.GameObjects.Beings;


import android.graphics.Bitmap;

import ru.temon137.labyrintharium.Controls.Control;


public class Gamer extends Being {

    public Gamer(Bitmap bitmap) {
        super(bitmap);
    }

    @Override
    public void action() {
        Control.getBalab().waitOne();
        Control.getBalab().reset();
    }

    @Override
    public void receiveDamage(int damage) {

    }

    @Override
    public void death() {

    }
}
