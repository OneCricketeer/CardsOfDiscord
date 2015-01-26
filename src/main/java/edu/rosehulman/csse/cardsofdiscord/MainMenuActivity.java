package edu.rosehulman.csse.cardsofdiscord;

import edu.rosehulman.csse.cardsofdiscord.dialog.ChangeNameDialog;
import edu.rosehulman.csse.cardsofdiscord.dialog.LegalDialog;
import edu.rosehulman.csse.cardsofdiscord.util.SessionManager;
import edu.rosehulman.csse.cardsofdiscord.view.MainTitleView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.Toast;

public class MainMenuActivity extends BaseActivity implements View.OnClickListener, OnMenuItemClickListener, ChangeNameDialog.OnChangeUsernameListener {
	private final String TAG = MainMenuActivity.class.getSimpleName();
    private MainTitleView mTitleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        mTitleView = (MainTitleView) findViewById(R.id.title_layout);
        mTitleView.setDiscordMode(mPrefs.isDiscordMode());
        enableButtons();

        if (!this.mPrefs.isLoggedIn()) {
        	new ChangeNameDialog().show(getFragmentManager(), "change-name-dialog");
        } else {
        	Toast.makeText(this, "Welcome " + mPrefs.getUserDetails().get(SessionManager.USER), Toast.LENGTH_SHORT).show();
        }
        
    }

	private void enableButtons() {
		findViewById(R.id.main_menu_menu_button).setOnClickListener(this);
        findViewById(R.id.pnp_button).setOnClickListener(this);
        findViewById(R.id.lan_button).setOnClickListener(this);
        findViewById(R.id.online_game_button).setOnClickListener(this);
        findViewById(R.id.how_play_button).setOnClickListener(this);
	}

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_menu_menu_button:
                showPopupMenu(v);
            	break;
            case R.id.pnp_button:
            	Log.i(TAG, "Pass and play");
                Intent intent = new Intent(MainMenuActivity.this, EnterNamesActivity.class);

                startActivity(intent);
            	break;
            case R.id.lan_button:
            	Log.i(TAG, "LAN Game");
            	break;
            case R.id.online_game_button:
            	Log.i(TAG, "Online Game");
            	break;
            case R.id.how_play_button:
            	Log.i(TAG, "How to play");
            	break;
            default:
                break;
        }

    }

    private void showPopupMenu(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_main, popup.getMenu());
        popup.setOnMenuItemClickListener(this);
        popup.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_change_name:
                new ChangeNameDialog().show(getFragmentManager(), "change-name-dialog");
                break;
            case R.id.menu_legal:
            	new LegalDialog().show(getFragmentManager(), "legal-dialog");
                break;
            default:
                break;
        }
        return false;
    }

	@Override
	public void onChangeUsername(String name) {
		this.mPrefs.createLoginSession(name);
		
	}

}
