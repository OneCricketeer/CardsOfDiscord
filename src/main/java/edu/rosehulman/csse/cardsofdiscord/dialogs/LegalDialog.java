package edu.rosehulman.csse.cardsofdiscord.dialogs;

import edu.rosehulman.csse.cardsofdiscord.R;
import edu.rosehulman.csse.cardsofdiscord.R.string;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;

public class LegalDialog extends DialogFragment {
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle(R.string.legal);
		builder.setMessage("blah blah blah");
		return builder.create();
	}
}
