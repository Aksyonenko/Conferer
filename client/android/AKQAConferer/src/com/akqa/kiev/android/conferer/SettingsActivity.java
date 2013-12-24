package com.akqa.kiev.android.conferer;

import com.akqa.kiev.android.conferer.utils.Constants;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

public class SettingsActivity extends FragmentActivity implements OnClickListener {
	private static final String TAG_SYNCHRONIZATION = "synchronization";
	private static final String TAG_SYNCHRONIZATION_MOBILE_NETWORKS = "synchroniztion_mobile";
	LayoutInflater inflater;

	private SharedPreferences preferences;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		preferences = getSharedPreferences(Constants.SETTINGS_SHARED_PREFERENCES_NAME, MODE_PRIVATE
				| MODE_MULTI_PROCESS);
		inflater = LayoutInflater.from(this);
		ViewGroup root = (ViewGroup) findViewById(R.id.activity_settings_root);
		root.removeAllViews();

		// Auto-sync item
		View itemView = constructSettingsItem(root, R.string.settings_synchronization_title,
				R.string.settings_synchronization_subtitle, TAG_SYNCHRONIZATION, this);
		((CheckBox) itemView.findViewById(R.id.settings_item_checkbox)).setChecked(preferences.getBoolean(
				Constants.SETTINGS_SYNCHRONIZATION, true));
		root.addView(itemView);

		// Network settings
		itemView = constructSettingsItem(root, R.string.settings_synchronization_mobile_networks_title,
				R.string.settings_synchronization_mobile_networks_subtitle, TAG_SYNCHRONIZATION_MOBILE_NETWORKS, this);
		((CheckBox) itemView.findViewById(R.id.settings_item_checkbox)).setChecked(preferences.getBoolean(
				Constants.SETTINGS_SYNCHRONIZATION_NETWORK_ALL, false));
		
		root.addView(itemView);
	}

	private View constructSettingsItem(ViewGroup root, int titleResId, int subtitleResId, Object tag,
			OnClickListener listener) {
		View itemView = inflater.inflate(R.layout.settings_item, root, false);
		itemView.setTag(tag);
		((TextView) itemView.findViewById(R.id.settings_item_title)).setText(titleResId);
		((TextView) itemView.findViewById(R.id.settings_item_subtitle)).setText(subtitleResId);
		itemView.setOnClickListener(listener);
		return itemView;
	} 

	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	public void onClick(View v) {
		Log.d(getClass().getName(), "clicked view tag:" + v.getTag());
		SharedPreferences.Editor editor = preferences.edit();
		((CheckBox) v.findViewById(R.id.settings_item_checkbox)).toggle();
		if (v.getTag().equals(TAG_SYNCHRONIZATION)) {
			boolean active = preferences.getBoolean(Constants.SETTINGS_SYNCHRONIZATION, true);
			Log.d(getClass().getName(), String.format("sync is %s,  changing to %s", active, !active));
			editor.putBoolean(Constants.SETTINGS_SYNCHRONIZATION, !active);
			editor.commit();
		} else if (v.getTag().equals(TAG_SYNCHRONIZATION_MOBILE_NETWORKS)) {
			boolean active = preferences.getBoolean(Constants.SETTINGS_SYNCHRONIZATION_NETWORK_ALL, false);
			Log.d(getClass().getName(), String.format("sync in all networks is %s,  changing to %s", active, !active));
			editor.putBoolean(Constants.SETTINGS_SYNCHRONIZATION, !active);
			editor.commit();
		}
	}
}
