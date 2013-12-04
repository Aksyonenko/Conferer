package com.akqa.kiev.android.conferer;

import com.akqa.kiev.android.conferer.fragments.ConferenceDetailsFragment;
import com.akqa.kiev.android.conferer.fragments.SessionDetailsFragment;
import com.akqa.kiev.android.conferer.service.ConfererDbService;
import com.akqa.kiev.android.conferer.service.ConfererWebService;
import com.akqa.kiev.android.conferer.utils.Constants;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.SearchView;

public class SessionDetailsActivity extends FragmentActivity implements OnSessionDetailsFragmentStartedListenter,
		OnDetailsFragmentStartedListener, OnSessionSelectedListener {
	private boolean isTwoPane = false;
	private ConfererWebService confererService;
	private ConfererDbService cs;
	private Long sessionId, conferenceId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_session_details);
		cs = new ConfererDbService(getApplicationContext());
		sessionId = getIntent().getLongExtra(Constants.BUNDLE_SESSION_ID, 0L);
		conferenceId = getIntent().getLongExtra(Constants.BUNDLE_CONFERENCE_ID, 0L);
	}

	@Override
	protected void onStart() {
		View frameLayoutView = findViewById(R.id.sessionDetailsRightFragmentContainer);
		if (frameLayoutView != null) {
			initSessionDetailsFragment();
			isTwoPane = true;
		}
		super.onStart();
	}

	private SessionDetailsFragment initSessionDetailsFragment() {
		SessionDetailsFragment sessionDetailsFragment = (SessionDetailsFragment) getSupportFragmentManager()
				.findFragmentById(R.id.sessionDetailsRightFragmentContainer);
		if (sessionDetailsFragment == null) {
			sessionDetailsFragment = new SessionDetailsFragment();
			FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
			transaction.replace(R.id.sessionDetailsRightFragmentContainer, sessionDetailsFragment);
			transaction.commit();
		}
		return sessionDetailsFragment;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);

		// Get the SearchView and set the searchable configuration
		SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
		SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();

		// Assumes current activity is the searchable activity
		searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
		searchView.setIconifiedByDefault(false);

		return true;
	}

	@Override
	public void onSessionDetailsFragmentStarted(SessionDetailsFragment fragment) {
		fragment.setSessionId(sessionId, conferenceId);
	}

	@Override
	public void onDetailsFragmentStarted(ConferenceDetailsFragment fragment) {
		fragment.setConferenceId(conferenceId);
	}

	@Override
	public void onSessionSelected(Long sessionId) {
		this.sessionId = sessionId;
		if(isTwoPane) {
			SessionDetailsFragment sessionDetailsFragment = initSessionDetailsFragment();
			sessionDetailsFragment.setSessionId(sessionId, conferenceId);
		} 
	}
}
