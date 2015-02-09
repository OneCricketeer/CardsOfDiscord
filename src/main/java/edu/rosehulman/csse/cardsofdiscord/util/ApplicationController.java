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
	private static GameController mGameController;

	@Override
	public void onCreate() {
		super.onCreate();
		sInstance = this;
		SessionManager.getInstance().init();
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
