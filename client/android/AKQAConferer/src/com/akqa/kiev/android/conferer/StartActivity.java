package com.akqa.kiev.android.conferer;

import com.akqa.kiev.android.conferer.view.conference.ConferencesPagerAdapter;
import com.akqa.kiev.android.conferer.web.ConfererService;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

public class StartActivity extends FragmentActivity {
	private ConferencesPagerAdapter conferencePagerAdapter;

	private ViewPager viewPager;

	private ConfererService confererService;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);
	}
}
