package ru.temon137.labyrintharium.World.GameObjects.Beings;


import android.graphics.Bitmap;

import ru.temon137.labyrintharium.Controls.Control;
import ru.temon137.labyrintharium.ManualResetEvent;
import ru.temon137.labyrintharium.World.GameObjects.Spell;


public class Gamer extends Being {
    private ManualResetEvent endStep;
    private Spell spell;


    public Gamer(Bitmap gamerBitmap, Bitmap spellBitmap) {
        super(gamerBitmap);
        endStep = new ManualResetEvent(false);
        spell = new Spell(spellBitmap, 5, 200);
    }

    @Override
    public void action() {
        try {
            endStep.waitOne();
        } catch (InterruptedException ignored) {
        }
        endStep.reset();
    }

    @Override
    public boolean move(Cource cource) {
        if (!super.move(cource))
            return false;

        endStep.set();
        return true;
    }

    @Override
    public void receiveDamage(int damage) {

    }

    @Override
    public void death() {
        despawn();
        Control.setControlEnabled(false);
        Control.getCurrentController().addLog("Вас поглотил призрак. После поглощения призрак исчез. " +
                                                      "Теперь лишь пыль будет обидать в этих покоях.");
    }

    public void shot(Cource cource) {
        if (cource == Cource.None)
            return;

        spell.spawn(coord);
        spell.shot(cource);
        try {
            spell.getDespawn().waitOne();
        } catch (InterruptedException ignored) {
        }
        endStep.set();
    }

    public Spell getSpell() {
        return spell;
    }
}
