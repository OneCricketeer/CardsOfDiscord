package edu.rosehulman.csse.cardsofdiscord;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import edu.rosehulman.csse.cardsofdiscord.model.Card;
import edu.rosehulman.csse.cardsofdiscord.model.Player;

public abstract class GameActivity extends BaseFragmentActivity implements
        CardSelectionFragment.OnCardSelectedListener,
		GameOverFragment.OnNewGameListener {

	public static final int STATE_HAND_DEVICE = 0;
	public static final int STATE_CARD_SELECT = 1;

	protected int mState = STATE_HAND_DEVICE;

    protected int mWhiteCardPicksLeft;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);
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

	public ArrayList<Player> getPlayers() {
		return mGameController.getPlayers();
	}
    protected ArrayList<Card> getWhiteCards() {
        return mGameController.getCurrentHand();
    }
    protected String getCurrentPlayerName() {
		return mGameController.getCurrentPlayer().getName();
	}
    public ArrayList<CardFragment> getCardFragments() {
        ArrayList<CardFragment> frags = new ArrayList<CardFragment>();

        for (Card wCard : getWhiteCards()) {
            frags.add(CardFragment.newInstance(wCard));
        }

        return frags;
    }

    protected Card getBlackCard() {
        return mGameController.getBlackCard();
    }

	public abstract void onCardsSelected(Integer position, Card card);

}
