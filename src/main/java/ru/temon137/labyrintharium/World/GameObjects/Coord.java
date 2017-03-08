package ru.temon137.labyrintharium.World.GameObjects;


public class Coord {
    private int x, y, z;
    //
    //-------------


    public Coord(int x, int y) {
        this(x, y, 0);
    }

    public Coord(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Coord))
            return false;

        Coord otherCoord = (Coord) obj;

        if (x != otherCoord.getX() || y != otherCoord.getY() || z != otherCoord.getZ())
            return false;

        return true;
    }
}

