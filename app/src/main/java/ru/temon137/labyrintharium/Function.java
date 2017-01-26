package ru.temon137.labyrintharium;

import ru.temon137.labyrintharium.World.GameObjects.Beings.Being;
import ru.temon137.labyrintharium.World.GameObjects.Coord;

public class Function {
    public static Being.Cource detectCource(Coord startCoord, Coord endCoord) {
        int deltaX;
        int deltaY;

        deltaX = endCoord.getX() - startCoord.getX();
        deltaY = endCoord.getY() - startCoord.getY();

        if (deltaX < 0 && deltaY <= 0)
            return Being.Cource.Left;
        if (deltaX >= 0 && deltaY < 0)
            return Being.Cource.Up;
        if (deltaX > 0 && deltaY >= 0)
            return Being.Cource.Right;
        if (deltaX <= 0 && deltaY > 0)
            return Being.Cource.Down;

        return Being.Cource.None;
    }
}
