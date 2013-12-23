package com.akqa.kiev.android.conferer;

import com.akqa.kiev.android.conferer.fragments.SpeakerDetailsFragment;
import com.akqa.kiev.android.conferer.utils.Constants;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;

public class SpeakerDetailsActivity extends FragmentActivity {

	private Long speakerId;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_speaker_details);
		speakerId = getIntent().getLongExtra(Constants.BUNDLE_SPEAKER_ID, 0L);
		SpeakerDetailsFragment speakerDetailsFragment = (SpeakerDetailsFragment) getSupportFragmentManager().findFragmentById(R.id.speakerDetailsFragment);
		if(speakerDetailsFragment != null && speakerDetailsFragment.isInLayout()) {
			speakerDetailsFragment.setSpeakerId(speakerId);
		}
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			break;
		}
		return super.onOptionsItemSelected(item);
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

}
