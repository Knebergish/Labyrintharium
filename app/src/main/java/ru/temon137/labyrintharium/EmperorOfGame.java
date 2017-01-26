package ru.temon137.labyrintharium;


import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import ru.temon137.labyrintharium.Activities.GameActivity;


public class EmperorOfGame {
    private Context context;
    private AppCompatActivity mainActivity;

    public EmperorOfGame(Context context, AppCompatActivity mainActivity) {
        this.context = context;
        this.mainActivity = mainActivity;
    }

    public void startGame() {

        mainActivity.startActivity(new Intent(context, GameActivity.class));
    }
}
