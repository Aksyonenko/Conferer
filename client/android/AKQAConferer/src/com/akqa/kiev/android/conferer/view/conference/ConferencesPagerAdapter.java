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
import android.util.Log;

import com.akqa.kiev.android.conferer.web.client.ConfererWebClient;
import com.akqa.kiev.android.conferer.web.json.ConferencesMonthsListJsonParser;
import com.akqa.kiev.android.conferer.web.json.exception.JsonParseException;

public class ConferencesPagerAdapter extends FragmentPagerAdapter {

	private List<ConferencesFragment> conferenceFragments;

	private ConfererWebClient webClient = new ConfererWebClient();

	private ConferencesMonthsListJsonParser conferencesMonthsListJsonParser = new ConferencesMonthsListJsonParser();

	private SimpleDateFormat simpleDateFormat;

	public ConferencesPagerAdapter(FragmentManager fm) {
		super(fm);

		simpleDateFormat = new SimpleDateFormat("MMM ''yy", Locale.ENGLISH);
		simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

		List<Long> conferencesMonths = loadConferencesMonths();
		if (conferencesMonths != null && !conferencesMonths.isEmpty()) {
			conferenceFragments = new ArrayList<ConferencesFragment>(
					conferencesMonths.size());
			for (int i = 0; i < conferencesMonths.size(); i++) {
				ConferencesFragment conferenceFragment = new ConferencesFragment();
				Bundle args = new Bundle();
				args.putLong(ConferencesFragment.MONTH_ARG,
						conferencesMonths.get(i));
				conferenceFragment.setArguments(args);
				conferenceFragments.add(conferenceFragment);
			}
		}
	}

	@Override
	public Fragment getItem(int position) {
		return conferenceFragments.get(position);
	}

	@Override
	public int getCount() {
		return conferenceFragments != null ? conferenceFragments.size() : 0;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		if (position >= 0 && position < conferenceFragments.size()) {
			Long month = conferenceFragments.get(position).getArguments()
					.getLong(ConferencesFragment.MONTH_ARG);
			return simpleDateFormat.format(month);
		}
		return null;
	}

	private List<Long> loadConferencesMonths() {
		String allConfMonths = webClient.getAllconferencesMonths();
		try {
			return conferencesMonthsListJsonParser
					.parseConferencesMonths(allConfMonths);
		} catch (JsonParseException e) {
			Log.e(getClass().getName(), e.getMessage());
		}
		return null;
	}
}
