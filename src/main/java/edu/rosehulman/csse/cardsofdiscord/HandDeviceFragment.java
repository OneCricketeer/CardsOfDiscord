package edu.rosehulman.csse.cardsofdiscord;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class HandDeviceFragment extends Fragment {

	public HandDeviceFragment() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater
				.inflate(R.layout.fragment_hand_device, container, false);
	}

}
