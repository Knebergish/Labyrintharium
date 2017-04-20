package ru.temon137.labyrintharium.DataBase;


import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class Administratum {
    private static Administratum instance;

    private SQLiteDatabase database;
    private DataBaseHelper dbHelper;

    private PlayersSubsystem playersSubsystem;

    private Administratum() {
    }

    public static Administratum getInstance() {
        return instance;
    }

    public static void open(Context context) throws SQLException {
        instance = new Administratum();

        instance.dbHelper = new DataBaseHelper(context);
        instance.database = instance.dbHelper.getWritableDatabase();

        instance.playersSubsystem = new PlayersSubsystem(instance);
    }

    public static void close() {
        instance.dbHelper.close();
    }


    SQLiteDatabase getDatabase() {
        return database;
    }

    public PlayersSubsystem getPlayersSubsystem() {
        return playersSubsystem;
    }
}
