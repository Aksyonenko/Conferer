package com.akqa.kiev.android.conferer.view.conference;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class ConferencesPagerAdapter extends FragmentPagerAdapter {

	private List<ConferencesFragment> conferenceFragments;

	public ConferencesPagerAdapter(FragmentManager fm) {
		super(fm);
		conferenceFragments = new ArrayList<ConferencesFragment>();
		for (int i = 0; i < 6; i++) {
			conferenceFragments.add(new ConferencesFragment());
		}
	}

	@Override
	public Fragment getItem(int position) {
		return conferenceFragments.get(position);
	}

	@Override
	public int getCount() {
		return 6;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		switch (position) {
		case 0:
			return "January 2013";
		case 1:
			return "February 2013";
		case 2:
			return "March 2013";
		case 3:
			return "April 2013";
		case 4:
			return "May 2013";
		case 5:
			return "December 2013";
		}
		return null;
	}
}
