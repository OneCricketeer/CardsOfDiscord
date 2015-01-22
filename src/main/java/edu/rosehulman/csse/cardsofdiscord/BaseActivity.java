package edu.rosehulman.csse.cardsofdiscord;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import edu.rosehulman.csse.cardsofdiscord.utils.SessionManager;

public abstract class BaseActivity extends Activity {
	
	protected final SessionManager mPrefs;
	
	public BaseActivity() {
		this.mPrefs = SessionManager.getInstance();
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		final View v = this.getWindow().getDecorView();
		Resources r = getResources();
		if (mPrefs.isDiscordMode()) {
			v.setBackgroundColor(r.getColor(R.color.black));
//			this.setTheme(R.style.Theme_Black);
		} else {
//			this.setTheme(R.style.Theme_Green);
			v.setBackgroundColor(r.getColor(R.color.green));
		}
	}
}
