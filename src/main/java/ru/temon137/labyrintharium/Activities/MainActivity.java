package ru.temon137.labyrintharium.Activities;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import ru.temon137.labyrintharium.DataBase.Administratum;
import ru.temon137.labyrintharium.DataBase.PlayersSubsystem;
import ru.temon137.labyrintharium.R;
import ru.temon137.labyrintharium.Settings;
import ru.temon137.labyrintharium.World.World;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        setContentView(R.layout.activity_main);

        Administratum.open(this);
        Settings.open(this);

        updatePlayerName();
    }

    private void updatePlayerName() {
        PlayersSubsystem playersSubsystem = Administratum.getInstance().getPlayersSubsystem();
        if (playersSubsystem.getCurrentPlayer() != null)
            ((TextView) findViewById(R.id.currentPlayerTextView)).setText(
                    playersSubsystem.getCurrentPlayer().getName()
            );
        else
            ((TextView) findViewById(R.id.currentPlayerTextView)).setText(
                    "Игрок не выбран"
            );
    }

    @Override
    public void onResume() {
        super.onResume();
        updatePlayerName();
    }

    public void startGameButton(View view) {
        if (Administratum.getInstance().getPlayersSubsystem().getCurrentPlayer() == null) {
            Toast toast = Toast.makeText(getApplicationContext(),
                                         "Игрок не выбран!", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }

        World.initialize();
        startActivity(new Intent(this, GameActivity.class));
        //this.overridePendingTransition(0, 0);
    }

    public void changeGameButton(View view) {
        startActivity(new Intent(this, ChangeGamerActivity.class));
        //this.overridePendingTransition(0, 0);
    }

    public void settingButton(View view) {
        if (Administratum.getInstance().getPlayersSubsystem().getCurrentPlayer() == null)
            return;

        startActivity(new Intent(this, SettingsActivity.class));
        //this.overridePendingTransition(0, 0);
    }

    public void helpButton(View view) {
        startActivity(new Intent(this, HelpActivity.class));
        //this.overridePendingTransition(0, 0);
    }

    public void exitButton(View view) {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        Administratum.close();
        Settings.close();

        super.onBackPressed();
        System.exit(0);
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "Destroying...");
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        Log.d(TAG, "Stopping...");
        super.onStop();
    }
}
