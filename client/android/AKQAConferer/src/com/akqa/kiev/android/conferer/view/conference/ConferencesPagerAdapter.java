package com.akqa.kiev.android.conferer.view.conference;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class ConferencesPagerAdapter extends FragmentPagerAdapter {

	private List<ConferencesFragment> conferencesFragments;

	private SimpleDateFormat simpleDateFormat;

	public ConferencesPagerAdapter(FragmentManager fm, List<Long> conferencesMonths) {
		super(fm);
		
		simpleDateFormat = new SimpleDateFormat("MMM ''yy", Locale.ENGLISH);
		simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
		
		if (conferencesMonths != null && !conferencesMonths.isEmpty()) {
			conferencesFragments = new ArrayList<ConferencesFragment>(
					conferencesMonths.size());
			for (int i = 0; i < conferencesMonths.size(); i++) {
				ConferencesFragment conferenceFragment = new ConferencesFragment();
				Bundle args = new Bundle();
				args.putLong(ConferencesFragment.MONTH_ARG,
						conferencesMonths.get(i));
				conferenceFragment.setArguments(args);
				conferencesFragments.add(conferenceFragment);
			}
		}
	}

	@Override
	public Fragment getItem(int position) {
		return conferencesFragments.get(position);
	}

	@Override
	public int getCount() {
		return conferencesFragments != null ? conferencesFragments.size() : 0;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		if (position >= 0 && position < conferencesFragments.size()) {
			Long month = conferencesFragments.get(position).getArguments()
					.getLong(ConferencesFragment.MONTH_ARG);
			return simpleDateFormat.format(month);
		}
		return null;
	}

}
