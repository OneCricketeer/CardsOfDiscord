/*******************************************************************************
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package edu.rosehulman.csse.cardsofdiscord.util;

import java.util.HashMap;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;

// http://stackoverflow.com/a/19613702
public class SessionManager {
	/**
	 * Singleton instance to manage SharedPreferences
	 */
	private static volatile SessionManager sInstance;
	private Context mContext;
	private SharedPreferences mPrefs;

	// Name of the SharedPreferences file
	private static final String PREF_NAME = SessionManager.class
			.getSimpleName();

	// User details
//	public static final String KEY_USERID = "edu.rosehulman.csse.cardsofdiscord.preferences.userID";
	public static final String USER = "edu.rosehulman.csse.cardsofdiscord.preferences.username";

	// Sharedpref login state
//	public static final String LOGGED_IN = "edu.rosehulman.csse.cardsofdiscord.preferences.isLoggedIn";

	public static final String DISCORD_MODE = "edu.rosehulman.csse.cardsofdiscord.preferences.discord";

	/**
	 * Gets a singleton instance of the session manager
	 *
	 * @return
	 */
	public static synchronized SessionManager getInstance() {
		if (sInstance == null)
			sInstance = new SessionManager();
		return sInstance;
	}

	public void init() {
		this.mContext = ApplicationController.getInstance()
				.getApplicationContext();
		this.mPrefs = mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
	}

	private SessionManager() {
		init();
	}

	/**
	 * Create login session with userID
	 * */
	public synchronized void createLoginSession(String userID) {
		Editor editor = mPrefs.edit();
		editor.putString(USER, userID);
//		editor.putString(KEY_USERID, userID);
//		editor.putBoolean(LOGGED_IN, true);
		// commit changes
		editor.commit();
	}

	/**
	 * Get stored session data
	 * */
	public synchronized HashMap<String, String> getUserDetails() {
		HashMap<String, String> details = new HashMap<String, String>();

//		details.put(KEY_USERID, mPrefs.getString(KEY_USERID, null));
		details.put(USER, mPrefs.getString(USER, null));
		
		return details;
	}

//	public synchronized static String getUserID() {
//		return SessionManager.getInstance().getUserDetails().get(KEY_USERID);
//	}

	public synchronized boolean isDiscordMode() {
		return mPrefs.getBoolean(DISCORD_MODE, true);
	}

	public synchronized void setDiscordMode(boolean discordMode) {
		Editor editor = mPrefs.edit();
		editor.putBoolean(DISCORD_MODE, discordMode);
		editor.commit();
	}

	public synchronized void clear() {
		Editor editor = mPrefs.edit();
		editor.clear();
		editor.commit();
	}

	public synchronized void logoutUser() {
		clear();
	}

	public synchronized boolean isLoggedIn() {
		return !TextUtils.isEmpty(mPrefs.getString(USER, null));
	}

	
}