package com.akqa.kiev.android.conferer;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.SearchView;

import com.akqa.kiev.android.conferer.fragments.ConferenceDetailsFragment;
import com.akqa.kiev.android.conferer.fragments.SessionDetailsFragment;
import com.akqa.kiev.android.conferer.fragments.SpeakerDetailsFragment;
import com.akqa.kiev.android.conferer.utils.Constants;

public class SessionDetailsActivity extends FragmentActivity implements OnSessionDetailsFragmentStartedListenter,
		OnDetailsFragmentStartedListener, OnSessionSelectedListener, SessionDetailsFragmentListener {
	private boolean isTwoPane = false;
	private Long sessionId, conferenceId, speakerId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_session_details);
		sessionId = getIntent().getLongExtra(Constants.BUNDLE_SESSION_ID, 0L);
		conferenceId = getIntent().getLongExtra(Constants.BUNDLE_CONFERENCE_ID, 0L);
		View frameLayoutView = findViewById(R.id.sessionDetailsRightFragmentContainer);
		if (frameLayoutView != null) {
			isTwoPane = true;
		}

		if (isTwoPane) {
			SessionDetailsFragment sessionDetailsFragment = null;
			sessionDetailsFragment = (SessionDetailsFragment) getSupportFragmentManager().findFragmentById(
					R.id.sessionDetailsRightFragmentContainer);
			if (sessionDetailsFragment == null) {
				sessionDetailsFragment = SessionDetailsFragment.newInstance(sessionId);
				FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
				transaction.replace(R.id.sessionDetailsRightFragmentContainer, sessionDetailsFragment);
				transaction.commit();
			}

			ConferenceDetailsFragment conferenceDetailsFragment = (ConferenceDetailsFragment) getSupportFragmentManager()
					.findFragmentById(R.id.sessionDetailsListFragment);
			conferenceDetailsFragment.setShowDescription(false);
			conferenceDetailsFragment.setConferenceId(conferenceId);
		} else {
			SessionDetailsFragment fragment = (SessionDetailsFragment) getSupportFragmentManager().findFragmentById(
					R.id.sessionDetailsFragment);
			fragment.setSessionId(sessionId, conferenceId);
		}
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
	}

	@Override
	public void onDetailsFragmentStarted(ConferenceDetailsFragment fragment) {
	}

	@Override
	public void onSessionSelected(Long sessionId) {
		this.sessionId = sessionId;
		if (isTwoPane) {
			FragmentManager fm = this.getSupportFragmentManager();
			fm.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
			SessionDetailsFragment sessionDetailsFragment = (SessionDetailsFragment) fm
					.findFragmentById(R.id.sessionDetailsRightFragmentContainer);
			if (sessionDetailsFragment != null) {
				sessionDetailsFragment.setSessionId(sessionId, conferenceId);
			} else {
				sessionDetailsFragment = SessionDetailsFragment.newInstance(sessionId);
				fm.beginTransaction().replace(R.id.sessionDetailsRightFragmentContainer, sessionDetailsFragment)
						.commit();
			}
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
				speakerFragment = SpeakerDetailsFragment.newInstance(speakerId);
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
}
