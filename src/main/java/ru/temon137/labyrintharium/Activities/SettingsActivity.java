package ru.temon137.labyrintharium.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ToggleButton;

import ru.temon137.labyrintharium.Controls.Position;
import ru.temon137.labyrintharium.DataBase.Administratum;
import ru.temon137.labyrintharium.DataBase.Player;
import ru.temon137.labyrintharium.R;
import ru.temon137.labyrintharium.Settings;

public class SettingsActivity extends AppCompatActivity {
    EditText scaleEditText;
    Spinner skinSpinner;
    ToggleButton controlPositionToggleButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        // перевод приложения в полноэкранный режим
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                             WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_settings);

        scaleEditText = (EditText) findViewById(R.id.editText2);
        scaleEditText.setText(Integer.toString(Settings.getCountCellInScreen()));

        settingSkinSpinner();

        controlPositionToggleButton = (ToggleButton) findViewById(R.id.toggleButton3);
        controlPositionToggleButton.setChecked(Settings.getControlPosition() == Position.RIGHT);
        controlPositionToggleButton.setOnCheckedChangeListener(
                (buttonView, isChecked) -> Settings.setControlPosition(
                        isChecked ? Position.RIGHT : Position.LEFT));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.overridePendingTransition(0, 0);
    }

    public void changeScaleButton(View view) {
        Settings.setCountCellInScreen(Integer.parseInt(scaleEditText.getText().toString()));
    }

    private void settingSkinSpinner() {
        skinSpinner = (Spinner) findViewById(R.id.skinSpinner);

        ArrayAdapter<?> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.skinsNames,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        skinSpinner.setAdapter(adapter);

        final Player currentPlayer = Administratum.getInstance().getPlayersSubsystem().getCurrentPlayer();
        skinSpinner.setSelection(currentPlayer.getSkin());

        skinSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent,
                                       View itemSelected,
                                       int selectedItemPosition,
                                       long selectedId) {

                Administratum.getInstance().getPlayersSubsystem().updatePlayer(
                        currentPlayer.get_id(),
                        currentPlayer.getName(),
                        (int) skinSpinner.getSelectedItemId()
                );
                Settings.setPlayerSkinIndex((int) skinSpinner.getSelectedItemId());
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public void changeControlPosition() {

    }
}
