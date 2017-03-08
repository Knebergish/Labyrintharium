package ru.temon137.labyrintharium;

public class Settings {
    private static boolean isInit = false;

    private static int displayWidth;
    private static int displayHeight;

    private static int countCellInScreen;
    private static int mainRegionLength;
    private static int controllerRegionWidth;
    private static int controllerRegionHeight;
    private static float coeff;

    private static int gamerSkinIndex;
    //=============


    public static void init(int displayWidth, int displayHeight, int countCellInScreen, int gamerSkinIndex) {
        if (isInit)
            throw new RuntimeException("Настройки уже были установлены! Повторная инициализация невозможна.");
        else
            isInit = true;

        Settings.displayWidth = displayWidth;
        Settings.displayHeight = displayHeight;

        Settings.countCellInScreen = countCellInScreen;
        Settings.mainRegionLength = Math.min(displayWidth, displayHeight);
        Settings.controllerRegionWidth = Settings.displayWidth;
        Settings.controllerRegionHeight = Settings.displayHeight - Settings.mainRegionLength;
        updateCoeff();

        Settings.gamerSkinIndex = gamerSkinIndex;
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


    public static int getGamerSkinIndex() {
        return gamerSkinIndex;
    }

    public static void setGamerSkinIndex(int gamerSkinIndex) {
        Settings.gamerSkinIndex = gamerSkinIndex;
    }
}
