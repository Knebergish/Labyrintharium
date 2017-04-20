package ru.temon137.labyrintharium.World.GameObjects.Beings;

import android.graphics.Bitmap;

import ru.temon137.labyrintharium.World.GameObjects.Coord;
import ru.temon137.labyrintharium.World.GameObjects.GameObject;
import ru.temon137.labyrintharium.World.GameObjects.SingleRenderComponent;
import ru.temon137.labyrintharium.World.World;


public abstract class Being extends GameObject {
    private int healthPoints;
    private int damage;
    private boolean isSpawned = false;

    public Being(Bitmap bitmap) {
        renderComponent = new SingleRenderComponent(bitmap);
    }


    @Override
    protected boolean isValidPosition(Coord newCoord) {
        boolean result;
        //result = !(newCoord.getX() < 0 || newCoord.getX() >= 6);
        //result = !(newCoord.getY() < 0 || newCoord.getY() >= 6) && result;
        result = World.isPassable(newCoord);// && result;
        return result;
    }

    @Override
    protected void changeCoordHandler() {

    }

    public boolean move(Cource cource) {
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

    public abstract void action();

    public abstract void receiveDamage(int damage);

    public abstract void death();

    @Override
    public boolean spawn(Coord coord) {
        if (setCoord(coord) && World.getBeingsMap().addT(this)) {
            isSpawned = true;
            return true;
        }
        return false;
    }

    @Override
    public void despawn() {
        World.getBeingsMap().removeT(coord);
        isSpawned = false;
    }

    public boolean isSpawned() {
        return isSpawned;
    }

    public enum Cource {Left, Up, Right, Down, None}
}
