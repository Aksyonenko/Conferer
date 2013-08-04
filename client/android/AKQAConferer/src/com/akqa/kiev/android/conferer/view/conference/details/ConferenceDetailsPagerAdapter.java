package com.akqa.kiev.android.conferer.view.conference.details;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.akqa.kiev.android.conferer.ConferenceDetailsActivity;
import com.akqa.kiev.android.conferer.model.ConferenceDetailsData;
import com.akqa.kiev.android.conferer.model.SessionData;
import com.akqa.kiev.android.conferer.utils.DateUtils;

public class ConferenceDetailsPagerAdapter extends FragmentPagerAdapter {

	private List<SessionsFragment> sessionsFragments;

	public ConferenceDetailsPagerAdapter(FragmentManager fm,
			ConferenceDetailsData conferenceDetailsData) {
		super(fm);
		List<SessionData> sessions = conferenceDetailsData.getSessions();
		if (sessions != null && !sessions.isEmpty()) {
			Map<Date, List<SessionData>> sortedSessions = DateUtils
					.sortSessionsByDays(sessions);
			sessionsFragments = new ArrayList<SessionsFragment>(
					sortedSessions.size());
			for (Iterator<Date> iterator = sortedSessions.keySet().iterator(); iterator
					.hasNext();) {
				Date date = iterator.next();
				SessionsFragment sessionsFragment = new SessionsFragment();
				Bundle args = new Bundle();
				args.putLong(ConferenceDetailsActivity.CONFERENCE_ID_ARG,
						conferenceDetailsData.getId());
				args.putLong(SessionsFragment.DATE_ARG, date.getTime());
				sessionsFragment.setArguments(args);
				sessionsFragment.setSessions(sortedSessions.get(date));
				sessionsFragments.add(sessionsFragment);
			}
		}
	}

	@Override
	public Fragment getItem(int position) {
		return sessionsFragments.get(position);
	}

	@Override
	public int getCount() {
		return sessionsFragments.size();
	}

	@Override
	public CharSequence getPageTitle(int position) {
		if (position >= 0 && position < sessionsFragments.size()) {
			return sessionsFragments.get(position).getTitle();
		}
		return null;
	}

}
