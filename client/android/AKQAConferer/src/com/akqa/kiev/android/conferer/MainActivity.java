package com.akqa.kiev.android.conferer;

import java.util.List;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.Window;

import com.akqa.kiev.android.conferer.service.ConfererWebService;
import com.akqa.kiev.android.conferer.utils.DateUtils;
import com.akqa.kiev.android.conferer.view.conference.ConferencesPagerAdapter;

public class MainActivity extends FragmentActivity {

	private ConferencesPagerAdapter conferencePagerAdapter;

	private ViewPager viewPager;

	private ConfererWebService confererService;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.detectNetwork().build();
		StrictMode.setThreadPolicy(policy);

		final boolean customTitleSupported = requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);

		setContentView(R.layout.activity_main);

		if (customTitleSupported) {
			getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
					R.layout.titlebar);
		}

		confererService = new ConfererWebService();
		List<Long> conferencesMonths = confererService.loadConferencesMonths();
		conferencePagerAdapter = new ConferencesPagerAdapter(
				getSupportFragmentManager(), conferencesMonths);

		viewPager = (ViewPager) findViewById(R.id.conferences_pager);
		viewPager.setAdapter(conferencePagerAdapter);
		viewPager.setCurrentItem(DateUtils
				.getNearestToCurrentDateIndex(conferencesMonths));
		// ImageButton listViewBtn = (ImageButton)
		// findViewById(R.id.listViewBtn);
		// listViewBtn.setEnabled(false);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
