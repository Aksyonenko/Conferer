package com.akqa.kiev.android.conferer;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;

import com.akqa.kiev.android.conferer.fragments.ConferenceDetailsFragment;
import com.akqa.kiev.android.conferer.service.ConfererService;
import com.akqa.kiev.android.conferer.utils.Constants;

public class ConferenceDetailActivity extends FragmentActivity implements OnDetailsFragmentStartedListener {
	
	private ConfererService confererService;
	
	private Long conferenceId;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		setContentView(R.layout.activity_conference_details);
		conferenceId = getIntent().getLongExtra(Constants.BUNDLE_CONFERENCE_ID, 0);
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
	public void onDetailsFragmentStarted(ConferenceDetailsFragment fragment) {
		fragment.setConferenceId(conferenceId);
	}
	
}
