package ru.temon137.labyrintharium.World.GameObjects.Beings;

import android.graphics.Bitmap;

import ru.temon137.labyrintharium.Controls.Control;
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

        if (coord.equals(World.getGamer().getCoord())) {
            World.getGamer().death();
        }

        hasStep = !hasStep;
    }

    @Override
    public void receiveDamage(int damage) {
        hp--;
        if (hp <= 0) {
            death();
        }
    }

    @Override
    public void death() {
        despawn();
        Control.getCurrentController().addLog("После очередного попадания в призрака заклинанием, " +
                                                      "он остановился, взвыл громче обычного, и схлопнулся.");
        Control.getCurrentController().addFutureLog("Теперь эти помещения принадлежат только вам. Делайте, что хотите.");
    }
}
