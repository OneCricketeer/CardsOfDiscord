package edu.rosehulman.csse.cardsofdiscord;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import edu.rosehulman.csse.cardsofdiscord.view.CircleView;

public class CardSelectionFragment extends Fragment {

    // private OnFragmentInteractionListener mListener;
    public static final String ARG_BLACK_CARD = "ARG_BLACK_CARD";
    public static final String ARG_WHITE_CARDS = "ARG_WHITE_CARDS";

    /**
     * The pager widget, which handles animation and allows swiping horizontally
     * to access previous and next wizard steps.
     */
    private ViewPager mPager;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private PagerAdapter mPagerAdapter;

    private String mBlackCard;
    private ArrayList<String> mWhiteCards;

    public CardSelectionFragment() {
        // Required empty public constructor
    }

    public static CardSelectionFragment newInstance(String blackCard,
                                                    ArrayList<String> whiteCards) {
        CardSelectionFragment fragment = new CardSelectionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_BLACK_CARD, blackCard);
        args.putStringArrayList(ARG_WHITE_CARDS, whiteCards);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mBlackCard = getArguments().getString(ARG_BLACK_CARD,
                    getString(R.string.black_card_text));
            mWhiteCards = getArguments().getStringArrayList(ARG_WHITE_CARDS);
            if (mWhiteCards == null) {
                mWhiteCards = new ArrayList<String>();
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_card_selection, container,
                false);

        // exitButton

        // black card text
        TextView blackCardTxt = (TextView) v.findViewById(R.id.black_card_text);
        blackCardTxt.setText(mBlackCard.trim());

        // viewpager
        mPager = (ViewPager) v.findViewById(R.id.pager);
        int margin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20 * 2, getResources().getDisplayMetrics());
        mPager.setPageMargin(-margin);
        mPagerAdapter = new CardPagerAdapter(getChildFragmentManager(),
                mWhiteCards);
        mPager.setAdapter(mPagerAdapter);

        // player scores
        LinearLayout scroller = (LinearLayout) v
                .findViewById(R.id.player_scores_scroller);
        GameActivity activity = (GameActivity) getActivity();
        ArrayList<String> names = getShortPlayerNames(activity.getPlayerNames(), 3);
        for (int i = 0; i < names.size(); i++) {
            CircleView cv = new CircleView(activity);
            cv.setPlayerName(names.get(i));
            cv.setPlayerScore(0);
            scroller.addView(cv);
        }
        return v;
    }

    private ArrayList<String> getShortPlayerNames(ArrayList<String> longNames, int maxNameLength) {
        if (longNames == null || longNames.isEmpty()) {
            return longNames;
        }
        StringBuilder sb = new StringBuilder();
        Map<String, Integer> nameMap = new LinkedHashMap<String, Integer>();
        for (String longName : longNames) {
            for (int i = 0; i < maxNameLength && i < longName.length(); i++) {
                sb.append(longName.charAt(i));
            }
            String shortName = sb.toString();
            int nameCount = 1;
            if (nameMap.containsKey(shortName)) {
                nameMap.put(shortName, nameMap.get(shortName) + 1);
                nameCount = nameMap.get(shortName);
                sb.setLength(maxNameLength - 1);
                sb.append(nameCount);
            }
            nameMap.put(sb.toString(), nameCount);
            sb.setLength(0);
        }

        return new ArrayList<String>(nameMap.keySet());

    }

    // @Override
    // public void onAttach(Activity activity) {
    // super.onAttach(activity);
    // try {
    // mListener = (OnFragmentInteractionListener) activity;
    // } catch (ClassCastException e) {
    // throw new ClassCastException(activity.toString()
    // + " must implement OnFragmentInteractionListener");
    // }
    // }

    @Override
    public void onDetach() {
        super.onDetach();
        // mListener = null;
    }

    // public interface OnFragmentInteractionListener {
    //
    // }

}
