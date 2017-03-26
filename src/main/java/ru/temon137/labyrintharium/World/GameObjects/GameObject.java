package ru.temon137.labyrintharium.World.GameObjects;

public abstract class GameObject {
    protected Coord coord;
    protected IRenderComponent renderComponent;

    public Coord getCoord() {
        return coord;
    }

    public boolean setCoord(Coord newCoord) {
        if (isValidPosition(newCoord)) {
            coord = newCoord;
            changeCoordHandler();
            return true;
        }

        return false;
    }

    protected abstract boolean isValidPosition(Coord newCoord);

    protected abstract void changeCoordHandler();

    public abstract boolean spawn(Coord coord);

    public abstract void despawn();

    public IRenderComponent getRenderComponent() {
        return renderComponent;
    }
}