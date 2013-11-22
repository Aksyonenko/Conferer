package com.akqa.kiev.android.conferer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import com.akqa.kiev.android.conferer.utils.Constants;
import com.akqa.kiev.android.conferer.web.ConfererService;

public class StartActivity extends FragmentActivity implements OnConferenceSelectedListener {


	private ConfererService confererService;
	
	private boolean isTwoPane = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);
	}
	
	@Override
	protected void onStart() {
		View frameLayoutView = findViewById(R.id.rightFragmentContainer);
		if(frameLayoutView != null) {
			isTwoPane = true;
		}
		super.onStart();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onConferenceSelected(Long conferenceId) {
		if(isTwoPane) {
			
		} else {
			Intent detailsIntent = new Intent(this, ConferenceDetailActivity.class);
			detailsIntent.putExtra(Constants.BUNDLE_CONFERENCE_ID, conferenceId);
			startActivity(detailsIntent);
		}
	}
}
