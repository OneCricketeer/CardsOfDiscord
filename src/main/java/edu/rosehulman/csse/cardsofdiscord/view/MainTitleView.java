package edu.rosehulman.csse.cardsofdiscord.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import edu.rosehulman.csse.cardsofdiscord.R;

public class MainTitleView extends LinearLayout {
    private final Context mContext;
    private boolean mIsDiscordMode = true;
    private TextView mDiscordTitle;
    
    public MainTitleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
    
        LayoutInflater.from(context).inflate(R.layout.main_title_view, this, true);
        this.mDiscordTitle = (TextView) findViewById(R.id.discord_title);

       
    }

    public MainTitleView(Context context) {
        this(context, null);
    }
    
    public void setDiscordMode(boolean b) {
        this.mIsDiscordMode = b;
        this.mDiscordTitle.setText(mIsDiscordMode ? R.string.discord : R.string.harmony);
    }

}
