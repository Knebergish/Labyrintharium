package ru.temon137.labyrintharium.DataBase;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DataBaseHelper extends SQLiteOpenHelper {
    private static String DB_PATH;
    private static String DB_NAME = "AdministratumDataBase.db";
    private final Context context;
    private SQLiteDatabase administratumDataBase;


    public DataBaseHelper(Context context) {
        super(context, DB_NAME, null, 1);

        this.context = context;
        DB_PATH = this.context.getFilesDir().getPath() + "/" + DB_NAME;
        initializeDataBase();
        openDataBase();
    }

    private void initializeDataBase() {
        boolean dbExist = dataBaseIsExist();

        if (!dbExist) {
            this.getReadableDatabase();

            try {
                copyDataBase();
            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }
    }

    private boolean dataBaseIsExist() {
        SQLiteDatabase checkDB;

        try {
            checkDB = SQLiteDatabase.openDatabase(DB_PATH, null, SQLiteDatabase.OPEN_READONLY);
        } catch (SQLiteException e) {
            return false;
        }

        checkDB.close();
        return true;
    }

    private void copyDataBase() throws IOException {
        InputStream myInput = context.getAssets().open(DB_NAME);
        OutputStream myOutput = new FileOutputStream(DB_PATH);

        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    public void openDataBase() throws SQLException {
        administratumDataBase = SQLiteDatabase.openDatabase(DB_PATH, null, SQLiteDatabase.OPEN_READWRITE);
    }

    @Override
    public SQLiteDatabase getWritableDatabase() {
        return administratumDataBase;
    }

    @Override
    public synchronized void close() {
        if (administratumDataBase != null)
            administratumDataBase.close();
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}