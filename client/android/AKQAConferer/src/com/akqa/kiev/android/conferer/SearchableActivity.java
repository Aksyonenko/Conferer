package com.akqa.kiev.android.conferer;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;

import com.akqa.kiev.android.conferer.db.ConferenceDao;
import com.akqa.kiev.android.conferer.db.SessionDao;
import com.akqa.kiev.android.conferer.db.SpeakerDao;

public class SearchableActivity extends Activity {


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		handleIntent(getIntent());
	}

	@Override
	protected void onNewIntent(Intent intent) {
		handleIntent(intent);
	}

	private void handleIntent(Intent intent) {
		if (Intent.ACTION_VIEW.equals(intent.getAction())) {
			long id = Long.parseLong(intent.getData().getLastPathSegment());
			String entity = intent.getExtras().getString(
					SearchManager.EXTRA_DATA_KEY);
			if (entity != null) {
				if (entity.equals(ConferenceDao.TABLE_NAME)) {

				} else if (entity.equals(SessionDao.TABLE_NAME)) {

				} else if (entity.equals(SpeakerDao.TABLE_NAME)) {

				}
			}
		}
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
			SearchView searchView = (SearchView) menu.findItem(R.id.search)
					.getActionView();
			searchView.setSearchableInfo(searchManager
					.getSearchableInfo(getComponentName()));
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
}
