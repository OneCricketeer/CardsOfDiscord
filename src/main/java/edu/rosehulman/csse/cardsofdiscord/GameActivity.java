package edu.rosehulman.csse.cardsofdiscord;

import java.util.ArrayList;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import edu.rosehulman.csse.cardsofdiscord.CardSelectionFragment.OnCardSelectedListener;
import edu.rosehulman.csse.cardsofdiscord.model.Card;
import edu.rosehulman.csse.cardsofdiscord.model.Player;

public class GameActivity extends BaseFragmentActivity implements
		HandDeviceFragment.OnDeviceHandedListener,
		OnCardSelectedListener,
		GameOverFragment.OnNewGameListener {

	public static final int STATE_HAND_DEVICE = 0;
	public static final int STATE_CARD_SELECT = 1;

	private int mState = STATE_HAND_DEVICE;

    private int mWhiteCardPicksLeft;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);

		Fragment f = null;
		switch (mState) {
		case STATE_HAND_DEVICE:
			if (!mGameController.getPlayers().isEmpty()) {
				f = HandDeviceFragment.newInstance(getCurrentPlayerName());
			}
			break;
		case STATE_CARD_SELECT:
			f = new CardSelectionFragment();
			break;
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

	public ArrayList<Player> getPlayers() {
		return mGameController.getPlayers();
	}

	private String getCurrentPlayerName() {
		return mGameController.getCurrentPlayer().getName();
	}

	@Override
	public void onHandedDevice() {
		Log.d(GameActivity.class.getSimpleName(), "Handed Device");
		mState = STATE_CARD_SELECT;
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
        Card bCard = getBlackCard();
        mWhiteCardPicksLeft = bCard.getNumPicks();
		CardSelectionFragment f = CardSelectionFragment.newInstance(
				bCard, getWhiteCards());
		ft.replace(R.id.container, f);
		ft.commit();
		if (mGameController.isJudging()) {
			Crouton.makeText(this, "You are Judging.", Style.INFO).show();
		}
	}

	private ArrayList<Card> getWhiteCards() {
		return mGameController.getCurrentHand();
	}

	private Card getBlackCard() {
		return mGameController.getBlackCard();
	}

	public void onCardsSelected(Card card) {
        boolean isJudging = mGameController.isJudging();
		mGameController.playCard(card); // changes judge

        if (mWhiteCardPicksLeft > 1 && !isJudging) {
            mState = STATE_CARD_SELECT;
            mWhiteCardPicksLeft--;
        } else {
            mState = STATE_HAND_DEVICE;
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            Fragment f = null;
            if (mGameController.isGameOver()){
                f = GameOverFragment.newInstance(mGameController.getWinners());
            }else{
                f = HandDeviceFragment
                        .newInstance(getCurrentPlayerName());
            }

            ft.replace(R.id.container, f);
            ft.commit();
        }
	}

	@Override
	public void onNewGame() {
		mGameController.playAgain();		
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		Fragment f =  HandDeviceFragment
					.newInstance(getCurrentPlayerName());		
		ft.replace(R.id.container, f);			
		ft.commit();
	}

}
