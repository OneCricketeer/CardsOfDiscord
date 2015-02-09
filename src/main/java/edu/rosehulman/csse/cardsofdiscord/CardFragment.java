package edu.rosehulman.csse.cardsofdiscord;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import edu.rosehulman.csse.cardsofdiscord.model.Card;
import edu.rosehulman.csse.cardsofdiscord.util.SessionManager;

public class CardFragment extends Fragment {
    private static final String ARG_CONTENT = "ARG_CONTENT";
    private String mContent;
    private boolean isSelected;
    
    private CardPagerAdapter.CardClickListener mListener;
    
    public void setOnClickListener(CardPagerAdapter.CardClickListener mListener) {
		this.mListener = mListener;
	}

    public static CardFragment newInstance(Card card) {
        CardFragment fragment = new CardFragment();
        Bundle args = new Bundle();
        args.putString(ARG_CONTENT, card.getContent());
        fragment.setArguments(args);
        return fragment;
    }

    public CardFragment() {
        // Required empty public constructor
    }
    
    public void onSelect() {
    	
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mContent = getArguments().getString(ARG_CONTENT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_card, container, false);
        TextView txt = (TextView) rootView.findViewById(R.id.white_card_text);
        txt.setText(mContent);
        ImageView stroke = (ImageView) rootView.findViewById(R.id.card_rounded_stroke);
        if (SessionManager.getInstance().isDiscordMode()){
        	stroke.setImageResource(R.drawable.rounded_corner_black);
        }else{
        	stroke.setImageResource(R.drawable.rounded_corner_green);
        }
        if (mListener != null) {
        	rootView.setOnClickListener(mListener);        	
        }
        return rootView;
    }

   

    

  

}
