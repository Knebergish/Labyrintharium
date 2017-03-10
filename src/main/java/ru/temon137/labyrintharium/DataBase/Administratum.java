package ru.temon137.labyrintharium.DataBase;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import ru.temon137.labyrintharium.Settings;

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

/*

    public long createTodo(String category, String summary, String description) {
        ContentValues initialValues = createContentValues(category, summary,
                description);

        return database.insert(DATABASE_TABLE, null, initialValues);
    }


    public boolean updateTodo(long rowId, String category, String summary,
                              String description) {
        ContentValues updateValues = createContentValues(category, summary,
                description);

        return database.update(DATABASE_TABLE, updateValues, KEY_ROWID + "="
                + rowId, null) > 0;
    }


    public boolean deleteTodo(long rowId) {
        return database.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
    }


    public Cursor fetchAllTodos() {
        return database.query(DATABASE_TABLE, new String[]{KEY_ROWID,
                        KEY_CATEGORY, KEY_SUMMARY, KEY_DESCRIPTION}, null, null, null,
                null, null);
    }


    public Cursor fetchTodo(long rowId) throws SQLException {
        Cursor mCursor = database.query(true, DATABASE_TABLE, new String[]{
                        KEY_ROWID, KEY_CATEGORY, KEY_SUMMARY, KEY_DESCRIPTION},
                KEY_ROWID + "=" + rowId, null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    private ContentValues createContentValues(String category, String summary,
                                              String description) {
        ContentValues values = new ContentValues();
        values.put(KEY_CATEGORY, category);
        values.put(KEY_SUMMARY, summary);
        values.put(KEY_DESCRIPTION, description);
        return values;
    }*/
}
