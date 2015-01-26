package edu.rosehulman.csse.cardsofdiscord.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import edu.rosehulman.csse.cardsofdiscord.R;

public class CircleView extends RelativeLayout {

	private Context mContext;

	private GradientDrawable mPlayerCircle;
	private GradientDrawable mScoreCircle;

	private TextView mPlayerName;
	private TextView mPlayerScore;

	private static final int RADIUS = 42;

	public CircleView(Context context) {
		this(context, null);
	}

	public CircleView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.mContext = context;
		this.mPlayerCircle = (GradientDrawable) getResources().getDrawable(
				R.drawable.circle_drawable);
		this.mScoreCircle = (GradientDrawable) getResources().getDrawable(
				R.drawable.circle_drawable);

		this.mPlayerCircle.setColor(getResources().getColor(R.color.white));
        this.mScoreCircle.setColor(getResources().getColor(R.color.red));

		LayoutInflater.from(mContext).inflate(R.layout.player_circle_view,
				this, true);
        
        mPlayerName = (TextView) findViewById(R.id.playerNameViewId);
        mPlayerScore = (TextView) findViewById(R.id.playerScore);

		updatePlayerCircleView();
		updateScoreCircleView();
		
	}


	private int SP_to_PX(int sp) {
		Resources r = mContext.getResources();
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp,
				r.getDisplayMetrics());
	}

	private void updatePlayerCircleView() {
		mPlayerName.setBackground(mPlayerCircle);
		int w = SP_to_PX(RADIUS);
		LayoutParams lp = new RelativeLayout.LayoutParams(w, w);
		int padd = RADIUS / 4;
		lp.setMargins(0, padd, padd, 0);
		mPlayerName.setLayoutParams(lp);
	}

	private void updateScoreCircleView() {
		mPlayerScore.setBackground(mScoreCircle);

		mPlayerScore.setPadding(0, 0, 0, 4);
		int w = SP_to_PX(RADIUS / 2);
		LayoutParams lp = new RelativeLayout.LayoutParams(w, w);

		lp.addRule(ALIGN_RIGHT, mPlayerName.getId());

		int padd = -RADIUS / 4;
		lp.setMargins(0, 0, padd, 0);

		mPlayerScore.setLayoutParams(lp);
	}

	public void setPlayerName(String name) {
		this.mPlayerName.setText(name);
	}

	public void setPlayerScore(int score) {
		this.mPlayerScore.setText("" + score);
	}

}
