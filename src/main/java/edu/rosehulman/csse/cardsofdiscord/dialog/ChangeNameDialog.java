package edu.rosehulman.csse.cardsofdiscord.dialog;

import edu.rosehulman.csse.cardsofdiscord.R;
import edu.rosehulman.csse.cardsofdiscord.util.SessionManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ChangeNameDialog extends DialogFragment {
	
	public interface OnChangeUsernameListener {
		public void onChangeUsername(String name);
	}
	
	private EditText mUsernameInput;
	private OnChangeUsernameListener mListener;
	
	@Override
	public void onAttach(Activity activity) {
		if (activity instanceof OnChangeUsernameListener) {
			mListener = (OnChangeUsernameListener) activity;
		} else {
			
			throw new ClassCastException("Activity must implement " + OnChangeUsernameListener.class.getSimpleName());
		}
		super.onAttach(activity);
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		if (!SessionManager.getInstance().isLoggedIn()) {
			builder.setTitle(R.string.set_name);
		} else {
			builder.setTitle(R.string.change_name);			
		}
		View v = LayoutInflater.from(getActivity()).inflate(R.layout.change_name_dialog, null);
		mUsernameInput = (EditText) v.findViewById(R.id.username_input);
		builder.setView(v);
		builder.setPositiveButton(android.R.string.ok, null);
		
		final AlertDialog diag = builder.create();
		diag.show();
		final Button posButton = diag.getButton(DialogInterface.BUTTON_POSITIVE);
		posButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String username = mUsernameInput.getText().toString().trim();
				if (username.isEmpty()) {
					Toast.makeText(getActivity(), "Username cannot be blank", Toast.LENGTH_SHORT).show();
					return;
				} 
				if (mListener != null) {
					mListener.onChangeUsername(username);
					diag.dismiss();
				}				
			}
		});
		return diag;
	}

}
