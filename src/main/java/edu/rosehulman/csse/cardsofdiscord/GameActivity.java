package edu.rosehulman.csse.cardsofdiscord;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;

public class GameActivity extends BaseFragmentActivity implements
		HandDeviceFragment.OnDeviceHandedListener {

	public static final String KEY_PLAYER_NAMES = "KEY_PLAYER_NAMES";

	private ArrayList<String> mPlayerNames;

	private int mCurrentPlayerIndex;

	public static final int STATE_HAND_DEVICE = 0;
	public static final int STATE_CARD_SELECT = 1;

	private int mState = STATE_HAND_DEVICE;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);

		Intent callingIntent = getIntent();
		if (callingIntent.getExtras() != null) {
			Bundle extras = callingIntent.getExtras();
			mPlayerNames = extras.getStringArrayList(KEY_PLAYER_NAMES);
		} else {
			mPlayerNames = new ArrayList<String>();
		}

		Fragment f = null;
		switch (mState) {
		case STATE_HAND_DEVICE:
			if (!mPlayerNames.isEmpty()) {
				f = HandDeviceFragment.newInstance(getCurrentPlayerName());
			}
			break;
		case STATE_CARD_SELECT:
			f = new CardSelectionFragment();
		default:
			break;
		}

		if (f != null) {
			FragmentManager fm = getSupportFragmentManager();
			FragmentTransaction ft = fm.beginTransaction();
			ft.add(R.id.container, f);
			ft.commit();
		}

	}

	public ArrayList<String> getPlayerNames() {
		return mPlayerNames;
	}

	private String getCurrentPlayerName() {
		return mPlayerNames.get(mCurrentPlayerIndex);
	}

	@Override
	public void onHandedDevice() {
		Log.d(GameActivity.class.getSimpleName(), "Handed Device");
		mState = STATE_CARD_SELECT;
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		// CardSelectionFragment f = new CardSelectionFragment();
		CardSelectionFragment f = CardSelectionFragment.newInstance(
				getBlackCard(), getWhiteCards());
		ft.replace(R.id.container, f);
		ft.commit();
	}

	private ArrayList<String> getWhiteCards() {
		ArrayList<String> cards = new ArrayList<String>();
		cards.add("Getting naked and watching Nickelodeon.");
		cards.add("Authentic Mexican cuisine.");
		cards.add("A time travel paradox.");
		cards.add("The Hamburgular.");
		cards.add("Being a busy adult with many important things to do.");
		cards.add("The economy.");
		cards.add("Ryan Gosling riding in on a white horse.");

		Collections.shuffle(cards);
		return cards;
	}

	private String getBlackCard() {
		return getString(R.string.favorite_food_truck);
	}

	public void onCardsSelected() {
		++mCurrentPlayerIndex;
		mState = STATE_HAND_DEVICE;
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		HandDeviceFragment f = HandDeviceFragment
				.newInstance(getCurrentPlayerName());
		ft.replace(R.id.container, f);
		ft.commit();
	}

}
