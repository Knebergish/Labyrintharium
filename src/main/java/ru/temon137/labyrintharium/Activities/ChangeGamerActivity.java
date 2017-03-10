package ru.temon137.labyrintharium.Activities;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Space;

import java.util.ArrayList;
import java.util.List;

import ru.temon137.labyrintharium.DataBase.Administratum;
import ru.temon137.labyrintharium.DataBase.Player;
import ru.temon137.labyrintharium.R;

public class ChangeGamerActivity extends AppCompatActivity {
    private RadioGroup radioGroup;
    private Administratum administratum;
    private List<PlayerRadio> playerRadioList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        setContentView(R.layout.activity_change_gamer);
        //////////////////////////////////////////////////////////////////

        radioGroup = (RadioGroup) findViewById(R.id.Gamers_Radio_Group);
        administratum = Administratum.getInstance();
        playerRadioList = new ArrayList<>();

        updatePlayers();
    }

    @Override
    public void onBackPressed() {
        Player selectedPlayer = getSelectedPlayer();
        if (selectedPlayer != null)
            administratum.getPlayersSubsystem().setCurrentPlayer(selectedPlayer.get_id());
        else
            administratum.getPlayersSubsystem().setCurrentPlayer(-1);

        super.onBackPressed();
    }

    private Player getSelectedPlayer() {
        for (PlayerRadio playerRadio : playerRadioList)
            if (playerRadio.getRadioButton().isChecked()) {
                return playerRadio.getPlayer();
            }
        return null;
    }

    private void updatePlayers() {
        radioGroup.removeAllViews();
        playerRadioList.clear();

        for (Player player : administratum.getPlayersSubsystem().getAllPlayers()) {
            PlayerRadio newPlayerRadio = new PlayerRadio(this, player);
            playerRadioList.add(newPlayerRadio);

            radioGroup.addView(newPlayerRadio.getRadioButton());

            Space space = new Space(this);
            space.setMinimumHeight(10);
            radioGroup.addView(space);
        }

        Player currentPlayer = administratum.getPlayersSubsystem().getCurrentPlayer();
        if (currentPlayer != null)
            for (PlayerRadio playerRadio : playerRadioList)
                if (playerRadio.getPlayer().get_id() == currentPlayer.get_id())
                    playerRadio.getRadioButton().setChecked(true);
    }

    public void addPlayer(View view) {
        administratum.getPlayersSubsystem().addPlayer(
                ((EditText) findViewById(R.id.playerNameEditText)).getText().toString()
        );
        updatePlayers();
    }

    public void removeSelectedPlayer(View view) {
        Player selectedPlayer = getSelectedPlayer();
        if (selectedPlayer != null)
            administratum.getPlayersSubsystem().removePlayer(selectedPlayer.get_id());


        updatePlayers();
    }
}


class PlayerRadio {
    private Player player;
    private RadioButton radioButton;

    public PlayerRadio(Context context, Player player) {
        this.player = player;

        Context themedContext = new ContextThemeWrapper(context, R.style.radio_button);
        LinearLayout.LayoutParams lEditParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );

        RadioButton radioButton = (RadioButton) android.view.LayoutInflater
                .from(themedContext).inflate(R.layout.what_button_template, null);
        radioButton.setLayoutParams(lEditParams);
        radioButton.setText(this.player.getName());
        this.radioButton = radioButton;
    }

    public Player getPlayer() {
        return player;
    }

    public RadioButton getRadioButton() {
        return radioButton;
    }
}
