package ru.temon137.labyrintharium.World;

import java.util.ArrayList;
import java.util.List;

import ru.temon137.labyrintharium.World.GameObjects.Coord;
import ru.temon137.labyrintharium.World.GameObjects.GameObject;


public class Map<T extends GameObject> {
    private List<T> list;

    public Map() {
        list = new ArrayList<T>();
    }

    public List<T> getAllT() {
        return new ArrayList<>(list);
    }

    public T getT(Coord coord) {
        for (T t : list)
            if (t.getCoord().equals(coord))
                return t;

        return null;
    }


    public boolean addT(T newT) {
        //assert(newT != null);

        if (getT(newT.getCoord()) != null)
            return false;

        list.add(newT);
        return true;
    }

    public void removeT(Coord coord) {
        list.remove(getT(coord));
    }
}