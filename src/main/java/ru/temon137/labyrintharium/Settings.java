package ru.temon137.labyrintharium;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.DisplayMetrics;
import android.view.Display;

import java.io.File;

import ru.temon137.labyrintharium.DataBase.Administratum;

public class Settings {
    private static int displayWidth;
    private static int displayHeight;

    private static int countCellInScreen;
    private static int mainRegionLength;
    private static int controllerRegionWidth;
    private static int controllerRegionHeight;
    private static float coeff;

    private static long currentPlayer;
    private static int playerSkinIndex;

    private static SharedPreferences preferences;
    //=============


    public static void open(Activity activity) {
        File file = new File(activity.getFilesDir().toString().replace("files", "") + "/shared_prefs/settings.xml");
        preferences = activity.getSharedPreferences("settings", Context.MODE_PRIVATE);


        if (file.exists()) {
            initFromFile();
        } else {
            init(activity);
            save();
        }
    }

    public static void close() {
        save();
    }

    private static void save() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();

        editor.putInt("displayWidth", Settings.displayWidth);
        editor.putInt("displayWidth", Settings.displayHeight);

        editor.putInt("countCellInScreen", Settings.countCellInScreen);
        editor.putInt("mainRegionLength", Settings.mainRegionLength);
        editor.putInt("controllerRegionWidth", Settings.controllerRegionWidth);
        editor.putInt("controllerRegionHeight", Settings.controllerRegionHeight);

        editor.putLong("currentPlayer", Settings.currentPlayer);

        editor.apply();
        editor.commit();
    }


    private static void initFromFile() {
        Settings.displayWidth = preferences.getInt("displayWidth", 0);
        Settings.displayHeight = preferences.getInt("displayHeight", 0);

        Settings.countCellInScreen = preferences.getInt("countCellInScreen", 0);
        Settings.mainRegionLength = preferences.getInt("mainRegionLength", 0);
        Settings.controllerRegionWidth = preferences.getInt("controllerRegionWidth", 0);
        Settings.controllerRegionHeight = preferences.getInt("controllerRegionHeight", 0);
        updateCoeff();

        Settings.currentPlayer = preferences.getLong("currentPlayer", 1);
        if (currentPlayer != -1)
            Settings.playerSkinIndex = Administratum.getInstance().getPlayersSubsystem().getCurrentPlayer().getSkin();
        else
            Settings.playerSkinIndex = 0;
    }

    private static void init(Activity activity) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        DisplayMetrics metricsB = new DisplayMetrics();
        display.getMetrics(metricsB);

        Settings.displayWidth = metricsB.widthPixels;
        Settings.displayHeight = metricsB.heightPixels;

        Settings.countCellInScreen = 11;
        Settings.mainRegionLength = Math.min(metricsB.widthPixels, metricsB.heightPixels);
        Settings.controllerRegionWidth = Settings.displayWidth;
        Settings.controllerRegionHeight = Settings.displayHeight - Settings.mainRegionLength;
        updateCoeff();

        Settings.currentPlayer = -1;
    }


    public static float getDisplayWidth() {
        return displayWidth;
    }

    public static float getDisplayHeight() {
        return displayHeight;
    }


    public static int getCountCellInScreen() {
        return countCellInScreen;
    }

    public static void setCountCellInScreen(int countCellInScreen) {
        Settings.countCellInScreen = countCellInScreen;
        updateCoeff();
    }

    public static int getMainRegionLength() {
        return mainRegionLength;
    }

    public static int getControllerRegionWidth() {
        return controllerRegionWidth;
    }

    public static int getControllerRegionHeight() {
        return controllerRegionHeight;
    }

    public static float getCoeff() {
        return coeff;
    }

    private static void updateCoeff() {
        coeff = mainRegionLength / countCellInScreen;
    }


    public static int getPlayerSkinIndex() {
        return playerSkinIndex;
    }

    public static void setPlayerSkinIndex(int playerSkinIndex) {
        Settings.playerSkinIndex = playerSkinIndex;
    }

    public static long getCurrentPlayer() {
        return currentPlayer;
    }

    public static void setCurrentPlayer(long currentPlayer) {
        Settings.currentPlayer = currentPlayer;
        if (currentPlayer != -1)
            Settings.playerSkinIndex = Administratum.getInstance().getPlayersSubsystem().getCurrentPlayer().getSkin();
        else
            Settings.playerSkinIndex = 0;
    }
}
