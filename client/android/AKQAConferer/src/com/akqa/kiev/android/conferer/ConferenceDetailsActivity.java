package com.akqa.kiev.android.conferer;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.Window;
import android.widget.TextView;

import com.akqa.kiev.android.conferer.model.ConferenceDetailsData;
import com.akqa.kiev.android.conferer.view.conference.details.ConferenceDetailsPagerAdapter;
import com.akqa.kiev.android.conferer.web.ConfererService;

public class ConferenceDetailsActivity extends FragmentActivity {

	private ConferenceDetailsPagerAdapter conferenceDetailsPagerAdapter;

	private ViewPager viewPager;

	private ConfererService confererService;

	public static String CONFERENCE_ID_ARG = "conferenceId";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		final boolean customTitleSupported = requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_conference_details);
		if (customTitleSupported) {
			getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
					R.layout.titlebar);
		}
		confererService = new ConfererService();
		initView();
	}

	private void initView() {
		Long conferenceId = (Long) getIntent().getSerializableExtra(
				CONFERENCE_ID_ARG);

		ConferenceDetailsData conferenceDetails = confererService
				.loadConferenceDetails(conferenceId);

		TextView titleView = (TextView) findViewById(R.id.conference_title);
		titleView.setTypeface(TypefaceRegistry.getTypeFace(
				getApplicationContext(), TypefaceRegistry.BOLD_COND));
		titleView.setText(conferenceDetails.getTitle());

		conferenceDetailsPagerAdapter = new ConferenceDetailsPagerAdapter(
				getSupportFragmentManager(), conferenceDetails);

		viewPager = (ViewPager) findViewById(R.id.conf_details_pager);
		viewPager.setAdapter(conferenceDetailsPagerAdapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
