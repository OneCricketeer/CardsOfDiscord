package edu.rosehulman.csse.cardsofdiscord;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;
import android.widget.PopupMenu.OnMenuItemClickListener;

public class MainMenuActivity extends Activity implements View.OnClickListener, OnMenuItemClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_menu);
		
		findViewById(R.id.main_menu_menu_button).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.main_menu_menu_button:
			showPopupMenu(v);
		}
		
	}

	private void showPopupMenu(View v) {
		PopupMenu popup = new PopupMenu(this, v);
	    MenuInflater inflater = popup.getMenuInflater();
	    inflater.inflate(R.menu.menu_main, popup.getMenu());
	    popup.setOnMenuItemClickListener(this);
	    popup.show();
	}

	@Override
	public boolean onMenuItemClick(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_change_name:
			Toast.makeText(this, "Change name", Toast.LENGTH_SHORT).show();
			break;
		case R.id.menu_legal:
			DialogFragment df = new DialogFragment() {
				@Override
				public Dialog onCreateDialog(Bundle savedInstanceState) {
					AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
					builder.setTitle(R.string.legal);
					builder.setMessage("blah blah blah");
					return builder.create();
				};
			};
			df.show(getFragmentManager(), "legal-dialog");
		default:
			break;
		}
		return false;
	}
}
