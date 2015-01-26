package edu.rosehulman.csse.cardsofdiscord;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;


public class HandDeviceFragment extends Fragment implements View.OnClickListener {

    public static final String TAG = HandDeviceFragment.class.getSimpleName();

    public interface OnDeviceHandedListener {
        public void onHandedDevice();
    }

    public static final String KEY_PLAYER_NAME = "KEY_PLAYER_NAME";
    private String mPlayerName;
    private OnDeviceHandedListener mListener;

    public HandDeviceFragment() {
		// Required empty public constructor
	}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            mPlayerName = args.getString(KEY_PLAYER_NAME, "????");
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof OnDeviceHandedListener) {
            mListener = (OnDeviceHandedListener) activity;
        } else {
            throw new ClassCastException(activity.toString() + " must implement " +
                            OnDeviceHandedListener.class.getSimpleName());
        }
    }

    public static HandDeviceFragment newInstance(String name) {
        HandDeviceFragment f = new HandDeviceFragment();
        Bundle args = new Bundle();
        args.putString(KEY_PLAYER_NAME, name);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.continue_button:
                if (mListener != null) {
                    mListener.onHandedDevice();
                } else {
                    Log.w(TAG, "Listener not attached!");
                }
                break;
            default:
                break;
        }
    }

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
        View v = inflater
                .inflate(R.layout.fragment_hand_device, container, false);
        TextView mText = (TextView) v.findViewById(android.R.id.text1);
        mText.setText(getString(R.string.hand_the_device, mPlayerName));
        v.findViewById(R.id.continue_button).setOnClickListener(this);
		return v;
	}

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

}
