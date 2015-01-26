package edu.rosehulman.csse.cardsofdiscord;

import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class CardPagerAdapter extends FragmentStatePagerAdapter {

	private ArrayList<String> mWhiteCards;
	
	public CardPagerAdapter(FragmentManager fm) {
		this(fm, new ArrayList<String>());
	}

	public CardPagerAdapter(FragmentManager fm, ArrayList<String> cards) {
		super(fm);
		this.mWhiteCards = cards;
	}

	@Override
	public Fragment getItem(int position) {
		String content = mWhiteCards == null ? "" + position : mWhiteCards
				.get(position);
		return CardFragment.newInstance(content);
	}

	@Override
	public int getCount() {
		return mWhiteCards == null ? 0 : mWhiteCards.size();
	}
}
