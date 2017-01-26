package ru.temon137.labyrintharium.World.GameObjects;

import ru.temon137.labyrintharium.World.World;

public interface IPhisicalComponent {
    boolean changePosition(World world, int newX, int newY);

}
