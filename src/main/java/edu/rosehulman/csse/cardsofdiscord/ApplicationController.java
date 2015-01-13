package edu.rosehulman.csse.cardsofdiscord;

import android.app.Application;

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
	
	@Override
	public void onCreate() {
		super.onCreate();
		sInstance = this;
		SessionManager.getInstance().init();
	}
}
