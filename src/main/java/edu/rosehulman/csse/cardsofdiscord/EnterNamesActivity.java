package edu.rosehulman.csse.cardsofdiscord;

import android.os.Bundle;
import android.view.View;

public class EnterNamesActivity extends BaseActivity implements View.OnClickListener {
	private static final String TAG = EnterNamesActivity.class.getSimpleName();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_enter_names);
		
		findViewById(R.id.add_player_button).setOnClickListener(this);
		findViewById(R.id.start_game_button).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.add_player_button:
			
			break;
		case R.id.start_game_button:
			
			break;

		default:
			break;
		}
		
	}
}
