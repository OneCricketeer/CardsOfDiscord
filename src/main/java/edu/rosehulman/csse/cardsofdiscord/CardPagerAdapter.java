package edu.rosehulman.csse.cardsofdiscord;

import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import edu.rosehulman.csse.cardsofdiscord.CardSelectionFragment.OnCardSelectedListener;

public class CardPagerAdapter extends FixedFragmentStatePagerAdapter {

    private final FragmentManager mFragmentManager;
    private FragmentTransaction mCurTransaction = null;

//	private ArrayList<Card> mWhiteCards;
	private OnCardSelectedListener mListener;

    private ArrayList<CardFragment> mFragments;

	public CardPagerAdapter(FragmentManager fm) {
		this(fm, new ArrayList<CardFragment>(),null);
	}

//	public CardPagerAdapter(FragmentManager fm, ArrayList<Card> cards, OnCardSelectedListener mListener) {
//		super(fm);
//        this.mFragmentManager = fm;
//		this.mWhiteCards = cards;
//		this.mListener = mListener;
//	}

    public CardPagerAdapter(FragmentManager fm, ArrayList<CardFragment> cardFragments, OnCardSelectedListener mListener) {
        super(fm);
        this.mFragmentManager = fm;
        this.mFragments = cardFragments;
        this.mListener = mListener;
    }

//    @Override
//    public void destroyItem(ViewGroup container, int position, Object object) {
//        Log.d("destroyItem", ""+position);
//        if (mCurTransaction == null) {
//            mCurTransaction = mFragmentManager.beginTransaction();
//            mCurTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
//        }
////        if (DEBUG) Log.v(TAG, "Detaching item #" + getItemId(position) + ": f=" + object
////                + " v=" + ((Fragment)object).getView());
//        mCurTransaction.remove((Fragment)object);
//    }

    @Override
	public Fragment getItem(final int position) {
		CardFragment frag = mFragments.get(position);
		frag.setOnClickListener(new CardClickListener(position));
		return frag;
	}

	@Override
	public float getPageWidth(int position) {
		return 0.9f;
	}

	@Override
	public int getCount() {
   		return mFragments.size();
	}

    class CardClickListener implements View.OnClickListener {
        private int mPosition;
        public CardClickListener(int position) {
            this.mPosition = position;
        }

        @Override
        public void onClick(View v) {
            mListener.onCardsSelected(mPosition, mFragments.get(mPosition).getCard());
            v.setVisibility(View.GONE);
//            mFragments.remove(mPosition);
//            notifyDataSetChanged();
        }

    }
}
