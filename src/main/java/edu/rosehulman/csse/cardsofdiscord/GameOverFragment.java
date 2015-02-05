package edu.rosehulman.csse.cardsofdiscord;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.abhi.gif.lib.AnimatedGifImageView;

import edu.rosehulman.csse.cardsofdiscord.model.Player;

/**
 * A simple {@link Fragment} subclass. Activities that contain this fragment
 * must implement the {@link GameOverFragment.OnCardSelectedListener}
 * interface to handle interaction events. Use the
 * {@link GameOverFragment#newInstance} factory method to create an instance of
 * this fragment.
 * 
 */
public class GameOverFragment extends Fragment {

    public interface OnNewGameListener {
        public void onNewGame();
    }
	
	private static final String ARG_WINNER = "ARG_WINNER";
	
	private OnNewGameListener mListener;

	private ArrayList<Player> mWinnerNames;
	private TextView tv;

	public static GameOverFragment newInstance(ArrayList<Player> winnerName) {
		GameOverFragment fragment = new GameOverFragment();
		Bundle args = new Bundle();
		args.putParcelableArrayList(ARG_WINNER, winnerName);
		fragment.setArguments(args);
		return fragment;
	}

	public GameOverFragment() {
		// Required empty public constructor
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			mWinnerNames = getArguments().getParcelableArrayList(ARG_WINNER);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View v = inflater
				.inflate(R.layout.fragment_game_over, container, false);
		tv = (TextView) v.findViewById(R.id.winnerText);
		
		if (mWinnerNames.size() == 0){
			tv.setText("This game has no winner!");
		}else if (mWinnerNames.size() == 1) {
			tv.setText("The winner is " + mWinnerNames.get(0).getName() + "!");
		}		else if (mWinnerNames.size() == 2) {
			tv.setText("The winners are " + mWinnerNames.get(0).getName() + " and " + mWinnerNames.get(1).getName() + "!");
		}else {
			String winners = "";
			for (int i =0 ; i <  mWinnerNames.size(); i++){
				if (i == mWinnerNames.size() - 1){
					winners += "and " + mWinnerNames.get(i).getName();
				}else{
					winners += mWinnerNames.get(i).getName() + ", ";
				}
				tv.setText("The winners are " + mWinnerNames.indexOf(0) + "!");
			}
		}
		
		
		((Button) v.findViewById(R.id.playAgainButton)).setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				mListener.onNewGame();
			}
		});
		
		((Button) v.findViewById(R.id.mainMenuButton)).setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getActivity(),MainMenuActivity.class);
				getActivity().startActivity(i);
			}
		});
		
		AnimatedGifImageView animatedGifImageView = ((AnimatedGifImageView) v.findViewById(R.id.fireworksImageView));
        animatedGifImageView.setAnimatedGif(R.raw.firework, AnimatedGifImageView.TYPE.AS_IS);
		
		return v;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
        if (activity instanceof OnNewGameListener) {
            mListener = (OnNewGameListener) activity;
        } else {
            throw new ClassCastException(activity.toString() + " must implement " +
                            OnNewGameListener.class.getSimpleName());
        }
	}

	@Override
	public void onDetach() {
		super.onDetach();
		mListener = null;
	}

}