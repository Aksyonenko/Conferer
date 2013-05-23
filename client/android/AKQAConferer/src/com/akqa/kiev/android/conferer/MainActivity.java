package com.akqa.kiev.android.conferer;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.Window;
import android.widget.ImageButton;

import com.akqa.kiev.android.conferer.view.conference.ConferencesPagerAdapter;

public class MainActivity extends FragmentActivity {

	private ConferencesPagerAdapter conferencePagerAdapter;

	private ViewPager viewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		final boolean customTitleSupported = requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);

		setContentView(R.layout.activity_main);

		if (customTitleSupported) {
			getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
					R.layout.titlebar);
		}

		conferencePagerAdapter = new ConferencesPagerAdapter(
				getSupportFragmentManager());

		viewPager = (ViewPager) findViewById(R.id.pager);
		viewPager.setAdapter(conferencePagerAdapter);
		ImageButton listViewBtn = (ImageButton) findViewById(R.id.listViewBtn);
		listViewBtn.setEnabled(false);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
