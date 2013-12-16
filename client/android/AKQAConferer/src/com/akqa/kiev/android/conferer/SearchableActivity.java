package com.akqa.kiev.android.conferer;

import static com.akqa.kiev.android.conferer.model.SearchData.TYPE_CONFERENCE;
import static com.akqa.kiev.android.conferer.model.SearchData.TYPE_SESSION;
import static com.akqa.kiev.android.conferer.model.SearchData.TYPE_SPEAKER;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import com.akqa.kiev.android.conferer.db.ConferenceDao;
import com.akqa.kiev.android.conferer.db.SessionDao;
import com.akqa.kiev.android.conferer.db.SpeakerDao;
import com.akqa.kiev.android.conferer.fragments.ConferenceDetailsFragment;
import com.akqa.kiev.android.conferer.fragments.SearchFragment;
import com.akqa.kiev.android.conferer.fragments.SessionDetailsFragment;
import com.akqa.kiev.android.conferer.fragments.SpeakerDetailsFragment;
import com.akqa.kiev.android.conferer.service.ConfererDbService;
import com.akqa.kiev.android.conferer.utils.Constants;

public class SearchableActivity extends FragmentActivity implements OnSearchResultSelectedListener,
		SessionDetailsFragmentListener, OnSessionSelectedListener {

	private ConfererDbService service;
	private boolean twoPane = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		service = new ConfererDbService(getApplicationContext());
		setContentView(R.layout.activity_search);
		View frameLayoutView = findViewById(R.id.searchRightFragmentContainer);
		if (frameLayoutView != null) {
			twoPane = true;
		}
		handleIntent(getIntent());
	}

	@Override
	protected void onNewIntent(Intent intent) {
		handleIntent(intent);
	}

	private void handleIntent(Intent intent) {
		Log.d(getClass().getName(), intent.getAction());
		if (Intent.ACTION_VIEW.equals(intent.getAction())) {
			long id = Long.parseLong(intent.getData().getLastPathSegment());
			String entity = intent.getExtras().getString(SearchManager.EXTRA_DATA_KEY);
			if (entity != null) {
				Intent redirectIntent = null;
				if (entity.equals(ConferenceDao.TABLE_NAME)) {
					redirectIntent = new Intent(this, ConferenceDetailActivity.class);
					redirectIntent.putExtra(Constants.BUNDLE_CONFERENCE_ID, id);
				} else if (entity.equals(SessionDao.TABLE_NAME)) {
					redirectIntent = new Intent(this, SessionDetailsActivity.class);
					redirectIntent.putExtra(Constants.BUNDLE_SESSION_ID, id);
					redirectIntent.putExtra(Constants.BUNDLE_CONFERENCE_ID, service.getConferenceId(id));
				} else if (entity.equals(SpeakerDao.TABLE_NAME)) {
					redirectIntent = new Intent(this, SpeakerDetailsActivity.class);
					redirectIntent.putExtra(Constants.BUNDLE_SPEAKER_ID, id);
				}

				if (redirectIntent != null) {
					redirectIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(redirectIntent);
					finish();
				}
			}
		} else if (intent.getAction().equals(Intent.ACTION_SEARCH)) {
			String query = intent.getStringExtra(SearchManager.QUERY);
			SearchFragment fragment = (SearchFragment) getSupportFragmentManager().findFragmentById(
					R.id.searchResultsFragment);
			if (fragment != null) {
				fragment.setSearchQuery(query);
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
			SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
			searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
			searchView.setIconifiedByDefault(false);
		}

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.search:
			onSearchRequested();
			return true;
		default:
			return false;
		}
	}

	@Override
	public void onSearchResultClick(int type, long id) {
		if (twoPane) {
			FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
			Fragment fragment = null;
			switch (type) {
			case TYPE_CONFERENCE:
				fragment = ConferenceDetailsFragment.newInstance(id, true);
				break;
			case TYPE_SESSION:
				fragment = SessionDetailsFragment.newInstance(id);
				break;
			case TYPE_SPEAKER:
				fragment = SpeakerDetailsFragment.newInstance(id);
				break;
			}
			if (fragment != null) {
				transaction.replace(R.id.searchRightFragmentContainer, fragment).commit();
			}
		} else {
			Intent intent = null;
			switch (type) {
			case TYPE_CONFERENCE:
				intent = new Intent(this, ConferenceDetailActivity.class);
				intent.putExtra(Constants.BUNDLE_CONFERENCE_ID, id);
				break;
			case TYPE_SESSION:
				intent = new Intent(this, SessionDetailsActivity.class);
				intent.putExtra(Constants.BUNDLE_SESSION_ID, id);
				break;
			case TYPE_SPEAKER:
				intent = new Intent(this, SpeakerDetailsActivity.class);
				intent.putExtra(Constants.BUNDLE_SPEAKER_ID, id);
				break;
			}
			if (intent != null) {
				startActivity(intent);
			}
		}
	}

	@Override
	public void onSpeakerClick(Long speakerId) {
		Fragment fragment = (Fragment) getSupportFragmentManager().findFragmentById(
				R.id.searchRightFragmentContainer);
		SpeakerDetailsFragment speakerFragment = null;
		if (fragment != null && fragment instanceof SpeakerDetailsFragment) {
			speakerFragment = (SpeakerDetailsFragment) fragment;
		}

		if (speakerFragment == null) {
			speakerFragment = SpeakerDetailsFragment.newInstance(speakerId);
			FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
			transaction.replace(R.id.searchRightFragmentContainer, speakerFragment).addToBackStack(null);
			transaction.commit();
		}		
	}

	@Override
	public void onSessionSelected(Long sessionId) {
		Intent intent = new Intent(this, SessionDetailsActivity.class);
		Long conferenceId = service.getConferenceId(sessionId);
		intent.putExtra(Constants.BUNDLE_SESSION_ID, sessionId);
		intent.putExtra(Constants.BUNDLE_CONFERENCE_ID, conferenceId);
		startActivity(intent);
	}

}
