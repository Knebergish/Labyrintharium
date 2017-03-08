package ru.temon137.labyrintharium.Activities;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import ru.temon137.labyrintharium.Administratum;
import ru.temon137.labyrintharium.DataBaseHelper;
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


        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics metricsB = new DisplayMetrics();
        display.getMetrics(metricsB);
        Settings.init(
                metricsB.widthPixels,
                metricsB.heightPixels,
                11,
                0
        );

        Administratum administratum = new Administratum(this);
        administratum.open();

        long y = administratum.createUser("Emperor");
        Cursor cursor = administratum.fetchAllUsers();
        int count = cursor.getCount();
        cursor.moveToNext();
        String s = cursor.getString(0);
        cursor.moveToNext();
    }

    public void startGameButton(View view) {
        World.initialize();
        startActivity(new Intent(this, GameActivity.class));
        //this.overridePendingTransition(0, 0);
    }

    public void changeGameButton(View view) {
        startActivity(new Intent(this, ChangeGamerActivity.class));
        //this.overridePendingTransition(0, 0);
    }

    public void settingButton(View view) {
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
        super.onBackPressed();
        System.exit(0);
    }

    /*@Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if(KeyEvent.KEYCODE_VOLUME_DOWN == keyCode)
            Settings.setCountCellInScreen(Settings.getCountCellInScreen() - 1);
        if(KeyEvent.KEYCODE_VOLUME_UP == keyCode)
            Settings.setCountCellInScreen(Settings.getCountCellInScreen() + 1);

        return false;
    }*/

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
