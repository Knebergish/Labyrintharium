package ru.temon137.labyrintharium.World.GameObjects.Blocks;

import android.graphics.Bitmap;

import ru.temon137.labyrintharium.World.GameObjects.Coord;
import ru.temon137.labyrintharium.World.GameObjects.GameObject;
import ru.temon137.labyrintharium.World.GameObjects.SingleRenderComponent;
import ru.temon137.labyrintharium.World.World;

public class Block extends GameObject {
    private boolean passableness;

    public Block(Bitmap bitmap, boolean passableness) {
        renderComponent = new SingleRenderComponent(bitmap);
        this.passableness = passableness;
    }

    @Override
    protected boolean isValidPosition(Coord newCoord) {
        return World.getBlocksMap().getT(newCoord) == null;
    }

    @Override
    protected void changeCoordHandler() {

    }

    @Override
    public boolean spawn(Coord coord) {
        return setCoord(coord) && World.getBlocksMap().addT(this);
    }

    @Override
    public void despawn() {
        //TODO: сделать нормальное удаление, с проверкой возможности.
        World.getBlocksMap().removeT(coord);
    }

    public boolean getPassableness() {
        return passableness;
    }
}
