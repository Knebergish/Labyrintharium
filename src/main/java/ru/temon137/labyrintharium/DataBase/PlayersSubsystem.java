package ru.temon137.labyrintharium.DataBase;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import ru.temon137.labyrintharium.Settings;

public class PlayersSubsystem {
    private Administratum administratum;
    private SQLiteDatabase database;

    PlayersSubsystem(Administratum administratum) {
        this.administratum = administratum;
        this.database = administratum.getDatabase();
    }

    public List<Player> getAllPlayers() {
        List<Player> players = new ArrayList<>();

        Cursor cursor = database.query("Players", new String[]{"_id", "name", "skin"}, null, null, null, null, null);
        while (cursor.moveToNext())
            players.add(new Player(cursor.getLong(0), cursor.getString(1), cursor.getInt(2)));
        cursor.close();

        return players;
    }

    public Player getPlayer(long _id) {
        Cursor cursor = database.query(true, "Players", new String[]{
                        "_id", "name", "skin"},
                "_id" + "=" + _id, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return new Player(cursor.getLong(0), cursor.getString(1), cursor.getInt(2));
    }

    public long addPlayer(String name) {
        ContentValues values = new ContentValues();
        values.put("name", name);

        return database.insert("Players", null, values);
    }

    public boolean removePlayer(long _id) {
        boolean deleted = database.delete("Players", "_id" + "=" + _id, null) > 0;
        if (deleted && Settings.getCurrentPlayer() == _id)
            setCurrentPlayer(-1);
        return deleted;
    }

    public boolean updatePlayer(long _id, String name, int skin) {
        ContentValues values = new ContentValues();
        values.put("_id", _id);
        values.put("name", name);
        values.put("skin", skin);

        return database.update("Players", values, "_id=" + _id, null) > 0;
    }


    public Player getCurrentPlayer() {
        if (Settings.getCurrentPlayer() == -1)
            return null;

        return getPlayer(Settings.getCurrentPlayer());
    }

    public void setCurrentPlayer(long _id) {
        Settings.setCurrentPlayer(_id);
    }
}
