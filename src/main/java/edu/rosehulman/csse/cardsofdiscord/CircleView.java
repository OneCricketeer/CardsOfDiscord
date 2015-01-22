package edu.rosehulman.csse.cardsofdiscord;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
		this.mScoreCircle.setColor(getResources().getColor(R.color.red));

		LayoutInflater.from(mContext).inflate(R.layout.player_circle_view,
				this, true);

		LayoutParams params = new RelativeLayout.LayoutParams(context, attrs);
		this.setLayoutParams(params);

		addPlayerCircleView();
		addScoreCircleView();
		
		setBackground(null);
		setPlayerName("?");
		setPlayerScore(0);
		
	}

	private int SP_to_PX(int sp) {
		Resources r = mContext.getResources();
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp,
				r.getDisplayMetrics());
	}

	private void addPlayerCircleView() {
		mPlayerName = new TextView(mContext);
		mPlayerName.setId(R.id.playerNameViewId);
		mPlayerName.setBackground(mPlayerCircle);
		mPlayerName.setGravity(Gravity.CENTER);
		Resources r = mContext.getResources();
		mPlayerName.setTextColor(r.getColor(R.color.black));
		int w = SP_to_PX(RADIUS);
		LayoutParams lp = new RelativeLayout.LayoutParams(w, w);
		int padd = RADIUS / 4;
		lp.setMargins(0, padd, padd, 0);
		mPlayerName.setLayoutParams(lp);
		addView(mPlayerName);
	}

	private void addScoreCircleView() {
		mPlayerScore = new TextView(mContext);
		mPlayerScore.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
		mPlayerScore.setGravity(Gravity.CENTER);
		Resources r = mContext.getResources();
		mPlayerScore.setBackground(mScoreCircle);
//		mPlayerScore.setBackgroundColor(r.getColor(R.color.red));
		mPlayerScore.setTextColor(r.getColor(R.color.white));
		mPlayerScore.setPadding(0, 0, 0, 4);
		int w = SP_to_PX(RADIUS / 2);
		LayoutParams lp = new RelativeLayout.LayoutParams(w, w);
		// int id = mPlayerName.getId();
		int id = R.id.player_circle_root_view;
//		lp.addRule(ALIGN_TOP, mPlayerName.getId());
		lp.addRule(ALIGN_RIGHT, mPlayerName.getId());

		int padd = -RADIUS / 4;
		lp.setMargins(0, 0, padd, 0);
		 lp.addRule(ALIGN_PARENT_TOP, RelativeLayout.TRUE);
		// lp.addRule(ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
		mPlayerScore.setLayoutParams(lp);
		addView(mPlayerScore);
	}

	public void setPlayerName(String name) {
		this.mPlayerName.setText(name);
	}

	public void setPlayerScore(int score) {
		this.mPlayerScore.setText("" + score);
	}

}
