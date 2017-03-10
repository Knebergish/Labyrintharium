package ru.temon137.labyrintharium.DataBase;


public class Player {
    private long _id;
    private String name;
    private int skin;


    public Player(long _id, String name, int skin) {
        this._id = _id;
        this.name = name;
        this.skin = skin;
    }


    public long get_id() {
        return _id;
    }

    public String getName() {
        return name;
    }

    public int getSkin() {
        return skin;
    }

}
