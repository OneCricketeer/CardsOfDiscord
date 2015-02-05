package edu.rosehulman.csse.cardsofdiscord.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import edu.rosehulman.csse.cardsofdiscord.R;

public class CircleView extends RelativeLayout {

	private Context mContext;

	private GradientDrawable mPlayerCircle;
	private GradientDrawable mScoreCircle;
	private GradientDrawable mJudgeCircle;
	private Drawable mHammerCircle;

	private TextView mPlayerName;
	private TextView mPlayerScore;

	private TextView mWhiteCircle;



	private static int RADIUS = 42;

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
		this.mJudgeCircle = (GradientDrawable) getResources().getDrawable(
				R.drawable.circle_drawable);
		this.mHammerCircle = (Drawable) getResources().getDrawable(
				R.drawable.judge_icon);

		this.mPlayerCircle.setColor(getResources().getColor(R.color.white));
        this.mScoreCircle.setColor(getResources().getColor(R.color.red));
        this.mJudgeCircle.setColor(getResources().getColor(R.color.alert_blue));

		LayoutInflater.from(mContext).inflate(R.layout.player_circle_view,
				this, true);
        
        mPlayerName = (TextView) findViewById(R.id.playerNameViewId);
        mPlayerScore = (TextView) findViewById(R.id.playerScore);
        mWhiteCircle = (TextView) findViewById(R.id.whiteCircleId);

        updateWhiteCircleView();
		updatePlayerCircleView();
		updateScoreCircleView();
	}


	private int SP_to_PX(int sp) {
		Resources r = mContext.getResources();
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp,
				r.getDisplayMetrics());
	}

	private void updatePlayerCircleView() {
		int w = SP_to_PX(RADIUS);
		LayoutParams lp = new RelativeLayout.LayoutParams(w, w);
		int padd = RADIUS / 4;
		lp.setMargins(0, padd, padd, 0);
		mPlayerName.setLayoutParams(lp);
	}
	
	private void updateWhiteCircleView() {
		mWhiteCircle.setBackground(mPlayerCircle);
		int w = SP_to_PX(RADIUS);
		LayoutParams lp = new RelativeLayout.LayoutParams(w, w);
		int padd = RADIUS / 4;
		lp.setMargins(0, padd, padd, 0);
		mWhiteCircle.setLayoutParams(lp);
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
	
	public void setJudge(boolean isJudge) {
		if (isJudge){			
			this.mPlayerName.setBackground(this.mHammerCircle);
			mPlayerName.setTextColor(getResources().getColor(R.color.white));
			this.mWhiteCircle.setBackground(this.mJudgeCircle);
		}else{
			mPlayerName.setTextColor(getResources().getColor(R.color.black));
			this.mPlayerName.setBackground(null);
			this.mWhiteCircle.setBackground(this.mPlayerCircle);
		}
	}
	
    @Override 
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
    	super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    	int size = Math.min(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(size, size);
        
        RADIUS = getHeight()/2;
		
		Log.d("onMeasure", getHeight() + "");
    }
    
    @Override
    protected void onDraw(Canvas canvas) {
    	updateWhiteCircleView();
		updatePlayerCircleView();
		updateScoreCircleView();
		
		Log.d("onDraw", getHeight() + "");
    }

}
