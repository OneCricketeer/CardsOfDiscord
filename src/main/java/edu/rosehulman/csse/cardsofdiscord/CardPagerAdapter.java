package edu.rosehulman.csse.cardsofdiscord;

import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;
import android.view.View;
import edu.rosehulman.csse.cardsofdiscord.CardSelectionFragment.OnCardSelectedListener;
import edu.rosehulman.csse.cardsofdiscord.model.Card;

public class CardPagerAdapter extends FragmentStatePagerAdapter {
	
	class CardClickListener implements View.OnClickListener {
		private int mPosition;
		public CardClickListener(int position) {
			this.mPosition = position;
		}
		
		@Override
		public void onClick(View v) {
			mListener.onCardsSelected(mWhiteCards.get(mPosition));
            v.setVisibility(View.GONE);
//            mWhiteCards.remove(mPosition);
            notifyDataSetChanged();
		}
	
	}

	private ArrayList<Card> mWhiteCards;
	private OnCardSelectedListener mListener;

	public CardPagerAdapter(FragmentManager fm) {
		this(fm, new ArrayList<Card>(),null);
	}

	public CardPagerAdapter(FragmentManager fm, ArrayList<Card> cards, OnCardSelectedListener mListener) {
		super(fm);
		this.mWhiteCards = cards;
		this.mListener = mListener;
	}

	@Override
	public Fragment getItem(final int position) {
		CardFragment frag = CardFragment.newInstance(mWhiteCards.get(position));
		frag.setOnClickListener(new CardClickListener(position));
		return frag;
	}

	@Override
	public float getPageWidth(int position) {
		return 0.9f;
	}

	@Override
	public int getCount() {
		return mWhiteCards == null ? 0 : mWhiteCards.size();
	}
}
