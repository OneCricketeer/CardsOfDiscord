package edu.rosehulman.csse.cardsofdiscord;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
import edu.rosehulman.csse.cardsofdiscord.model.GameController;
import edu.rosehulman.csse.cardsofdiscord.util.ApplicationController;

public class EnterNamesActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = EnterNamesActivity.class.getSimpleName();

    private static final int MAX_NUM_PLAYERS = 7;

    private LinearLayout mNameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_names);

        mNameLayout = (LinearLayout) findViewById(R.id.names_list_layout);
        
        
        String[] name = new String[] {"Teddy", "Jordan", "Nils"};
        for (int i = 0; i < 3; i++) {
        	View v = mNameLayout.getChildAt(i);
        	((EditText) v).setText(name[i]);
        }

        enableButtons();
    }

    private void enableButtons() {
        findViewById(R.id.add_player_button).setOnClickListener(this);
        findViewById(R.id.start_game_button).setOnClickListener(this);
    }

    private ArrayList<String> getPlayerNames() {
        Set<String> playerNames = new HashSet<String>();
        String name = "";
        for (int i = 0; i < mNameLayout.getChildCount(); i++) {
            View v = mNameLayout.getChildAt(i);
            if (v instanceof EditText) {
                name = ((EditText) v).getText().toString();
            } else if (v instanceof RelativeLayout) {
                EditText input = (EditText) v.findViewById(R.id.player_name_input);
                name = input.getText().toString();
            }
            if (TextUtils.isEmpty(name)) {
                Toast.makeText(this, getString(R.string.player_names_not_blank), Toast.LENGTH_SHORT).show();
                return null;
            }
            if (playerNames.contains(name)) {
                Toast.makeText(this, getString(R.string.player_names_unique), Toast.LENGTH_SHORT).show();
                return null;
            }
            playerNames.add(name);
        }

        return new ArrayList<String>(playerNames);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_player_button:
                if (mNameLayout.getChildCount() < MAX_NUM_PLAYERS) {
                    View input = getLayoutInflater().inflate(R.layout.player_name_input_delete, null);
                    ImageButton deleteButton = (ImageButton) input.findViewById(R.id.remove_player_button);
                    deleteButton.setTag(input);
                    deleteButton.setOnClickListener(this);
                    input.requestFocus();
                    mNameLayout.addView(input);
                } else {
                    Toast.makeText(this, getString(R.string.max_players, MAX_NUM_PLAYERS), Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.start_game_button:
                ArrayList<String> names = getPlayerNames();
                if (names != null) {
                    Intent intent = new Intent(this, GameActivity.class);
                    GameController gc = ApplicationController.getGameController();
                    gc.newGame(names);
                    //intent.putStringArrayListExtra(GameActivity.KEY_PLAYER_NAMES, names);
                    startActivity(intent);
                    finish();
                }
                break;
            case R.id.remove_player_button:
                if (v.getTag() != null) {
                    Log.d(TAG, v.getTag().toString());
                    View viewToRemove = (View) v.getTag();
                    mNameLayout.removeView(viewToRemove);
                }
                break;
            default:
                break;
        }

    }
}
