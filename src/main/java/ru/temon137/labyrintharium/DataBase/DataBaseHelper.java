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

    private SQLiteDatabase administratumDataBase;
    private final Context context;

    /**
     * Конструктор
     * Принимает и сохраняет ссылку на переданный контекст для доступа к ресурсам приложения
     */
    public DataBaseHelper(Context context) {
        super(context, DB_NAME, null, 1);

        this.context = context;
        DB_PATH = this.context.getFilesDir().getPath() + "/" + DB_NAME;
        initializeDataBase();
        openDataBase();
    }

    /**
     * Создает пустую базу данных и перезаписывает ее нашей собственной базой
     */
    private void initializeDataBase() {
        boolean dbExist = dataBaseIsExist();

        if (!dbExist) {
            //вызывая этот метод создаем пустую базу, позже она будет перезаписана
            this.getReadableDatabase();

            try {
                copyDataBase();
            } catch (IOException e) {
                //TODO: нормальная обработка отсутствия БД
                throw new Error("Error copying database");
            }
        }
    }

    /**
     * Проверяет, существует ли уже эта база, чтобы не копировать каждый раз при запуске приложения
     *
     * @return true если существует, false если не существует
     */
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

    /**
     * Копирует базу из папки assets заместо созданной локальной БД
     * Выполняется путем копирования потока байтов.
     */
    private void copyDataBase() throws IOException {
        //Открываем локальную БД как входящий поток
        InputStream myInput = context.getAssets().open(DB_NAME);

        //Открываем пустую базу данных как исходящий поток
        OutputStream myOutput = new FileOutputStream(DB_PATH);

        //перемещаем байты из входящего файла в исходящий
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

        //закрываем потоки
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

    // Здесь можно добавить вспомогательные методы для доступа и получения данных из БД
    // вы можете возвращать курсоры через "return administratumDataBase.query(....)", это облегчит их использование
    // в создании адаптеров для ваших view
}