package edu.rosehulman.csse.cardsofdiscord.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import edu.rosehulman.csse.cardsofdiscord.R;
import edu.rosehulman.csse.cardsofdiscord.model.GameController;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;

public class ApplicationController extends Application {
	public static final String TAG = ApplicationController.class
			.getSimpleName();

	/**
	 * Singleton ApplicationController
	 */
	private static ApplicationController sInstance;

	public static synchronized ApplicationController getInstance() {
		return sInstance;
	}

	private static AppTheme sTheme;

	private static GameController mGameController;

	public static enum AppTheme {
		BLACK, GREEN
	};

	@Override
	public void onCreate() {
		super.onCreate();
		sInstance = this;
		SessionManager.getInstance().init();
	}

	/**
	 * Set the theme of the Activity, and restart it by creating a new Activity
	 * of the same type.
	 */
	public static void changeToTheme(Activity activity, AppTheme theme) {
		sTheme = theme;
		activity.finish();

		activity.startActivity(new Intent(activity, activity.getClass()));
	}

	/** Set the theme of the activity, according to the configuration. */
	public static void setActivityBackground(Activity activity) {
		switch (sTheme) {
		default:
		case BLACK:
			break;
		case GREEN:
			activity.setTheme(R.style.Theme_Green);
		}
	}
	
	public static GameController getGameController(){
		if (mGameController == null){
			mGameController = new GameController();			
		}
		return mGameController;
		
	}

	/** Return the requested assets file */
	public static File getAssetFile(String filename) throws RuntimeException {
		File f = new File(getInstance().getCacheDir() + "/" + filename);
		if (!f.exists())
			try {

				InputStream is = getInstance().getAssets().open(filename);
				int size = is.available(); 
				byte[] buffer = new byte[size];
				is.read(buffer);
				is.close();

				FileOutputStream fos = new FileOutputStream(f);
				fos.write(buffer);
				fos.close();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		return f;
	}
}
