package com.akqa.kiev.android.conferer;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.SearchView;

import com.akqa.kiev.android.conferer.db.ConfererDatabase;
import com.akqa.kiev.android.conferer.fragments.ConferenceDetailsFragment;
import com.akqa.kiev.android.conferer.gcm.GcmClient;
import com.akqa.kiev.android.conferer.service.ConfererDbService;
import com.akqa.kiev.android.conferer.service.ConfererWebService;
import com.akqa.kiev.android.conferer.utils.Constants;

public class StartActivity extends FragmentActivity implements OnConferenceSelectedListener, OnDetailsFragmentStartedListener {


	private boolean isTwoPane = false;
	private ConfererWebService confererService;
	private ConfererDbService cs;
	private GcmClient gcmClient = new GcmClient();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);
		cs = new ConfererDbService(getApplicationContext());

		if (!gcmClient.checkPlayServices(this)) {
			Log.i(getClass().getName(),
					"No valid Google Play Services APK found.");
		}
	}

	@Override
	protected void onStart() {
		View frameLayoutView = findViewById(R.id.rightFragmentContainer);
		if (frameLayoutView != null) {
			initDetailsFragment();
			isTwoPane = true;
		}
		super.onStart();
	}
	
	@Override
	protected void onResume() {
	    super.onResume();
	    gcmClient.checkPlayServices(this);
	}
	
	private ConferenceDetailsFragment initDetailsFragment() {
		ConferenceDetailsFragment detailsFragment = (ConferenceDetailsFragment) getSupportFragmentManager()
				.findFragmentById(R.id.rightFragmentContainer);
		if(detailsFragment == null) {
			detailsFragment = new ConferenceDetailsFragment();
			FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
			transaction.replace(R.id.rightFragmentContainer, detailsFragment);
			transaction.commit();
		}
		return detailsFragment;
	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);

		// Get the SearchView and set the searchable configuration
		SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
		SearchView searchView = (SearchView) menu.findItem(R.id.search)
				.getActionView();

		// Assumes current activity is the searchable activity
		searchView.setSearchableInfo(searchManager
				.getSearchableInfo(getComponentName()));
		searchView.setIconifiedByDefault(false);

		return true;
	}

	@Override
	public void onConferenceSelected(Long conferenceId) {
		if (isTwoPane) {
			ConferenceDetailsFragment detailsFragment = initDetailsFragment();
			detailsFragment.setConferenceId(conferenceId);
		} else {
			Intent detailsIntent = new Intent(this, ConferenceDetailActivity.class);
			detailsIntent.putExtra(Constants.BUNDLE_CONFERENCE_ID, conferenceId);
			startActivity(detailsIntent);
		}
	}

	@Override
	public void onDetailsFragmentStarted(ConferenceDetailsFragment fragment) {
		// TODO Auto-generated method stub	
	}
}
