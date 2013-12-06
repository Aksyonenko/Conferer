package com.akqa.kiev.android.conferer;

import com.akqa.kiev.android.conferer.fragments.ConferenceDetailsFragment;
import com.akqa.kiev.android.conferer.fragments.SessionDetailsFragment;
import com.akqa.kiev.android.conferer.fragments.SpeakerDetailsFragment;
import com.akqa.kiev.android.conferer.service.ConfererDbService;
import com.akqa.kiev.android.conferer.service.ConfererWebService;
import com.akqa.kiev.android.conferer.utils.Constants;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.SearchView;

public class SessionDetailsActivity extends FragmentActivity implements OnSessionDetailsFragmentStartedListenter,
		OnDetailsFragmentStartedListener, OnSessionSelectedListener, SessionDetailsFragmentListener, SpeakerDetailsFragmentListener {
	private boolean isTwoPane = false;
	private ConfererWebService confererService;
	private ConfererDbService cs;
	private Long sessionId, conferenceId, speakerId;

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
		SessionDetailsFragment sessionDetailsFragment = null;
		Fragment fragment = (Fragment) getSupportFragmentManager().findFragmentById(
				R.id.sessionDetailsRightFragmentContainer);
		if(fragment != null && fragment instanceof SessionDetailsFragment) {
			sessionDetailsFragment = (SessionDetailsFragment) fragment;
		}
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
		if (isTwoPane) {
			SessionDetailsFragment sessionDetailsFragment = initSessionDetailsFragment();
			sessionDetailsFragment.setSessionId(sessionId, conferenceId);
		}
	}

	@Override
	public void onSpeakerClick(Long speakerId) {
		this.speakerId = speakerId;
		if (isTwoPane) {
			Fragment fragment = (Fragment) getSupportFragmentManager().findFragmentById(
					R.id.sessionDetailsRightFragmentContainer);
			SpeakerDetailsFragment speakerFragment = null;
			if (fragment != null && fragment instanceof SpeakerDetailsFragment) {
				speakerFragment = (SpeakerDetailsFragment) fragment;
			}

			if (speakerFragment == null) {
				speakerFragment = new SpeakerDetailsFragment();
				FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
				transaction.setCustomAnimations(R.anim.anim_speaker_enter, R.anim.anim_speaker_exit,
						R.anim.anim_speaker_pop_enter, R.anim.anim_speaker_pop_exit);
				transaction.replace(R.id.sessionDetailsRightFragmentContainer, speakerFragment).addToBackStack(null);
				transaction.commit();
			}
		} else {
			Intent speakerIntent = new Intent(this, SpeakerDetailsActivity.class);
			speakerIntent.putExtra(Constants.BUNDLE_SPEAKER_ID, speakerId);
			startActivity(speakerIntent);
		}
	}

	@Override
	public void onSpeakerDetailsFragmentStart(SpeakerDetailsFragment fragment) {
		fragment.setSpeakerId(speakerId);		
	}
}
