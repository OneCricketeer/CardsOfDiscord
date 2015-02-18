package edu.rosehulman.csse.cardsofdiscord;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;
import edu.rosehulman.csse.cardsofdiscord.model.Card;

/**
 * Activity used for Local Game play. Hand Device Screen included.
 */
public class LocalGameActivity extends GameActivity implements
        HandDeviceFragment.OnDeviceHandedListener {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCardsSelected(Integer position, Card card) {
        boolean isJudging = mGameController.isJudging();
        mGameController.playCard(card); // changes judge

        if (mWhiteCardPicksLeft > 1 && !isJudging) {
            mState = STATE_CARD_SELECT;

            // "Remove" a card
            CardSelectionFragment f = (CardSelectionFragment) getSupportFragmentManager().findFragmentById(R.id.container);
            ViewPager pager = (ViewPager) f.getView().findViewById(R.id.pager);
            CardPagerAdapter pagerAdapter = (CardPagerAdapter) pager.getAdapter();
//            pager.setAdapter(null);
//            if (pagerAdapter != null && position != null) {
//                pagerAdapter.destroyItem(pager, position, pagerAdapter.getItem(position));
//                pagerAdapter.notifyDataSetChanged();
//                pager.setAdapter(pagerAdapter);
//            }

            mWhiteCardPicksLeft--;
        } else {
            mState = STATE_HAND_DEVICE;
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            Fragment f = null;
            if (mGameController.isGameOver()) {
                f = GameOverFragment.newInstance(mGameController.getWinners());
            } else {
                f = HandDeviceFragment
                        .newInstance(getCurrentPlayerName());
            }

            ft.replace(R.id.container, f);
            ft.commit();
        }

    }

    @Override
    public void onHandedDevice() {
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



}
