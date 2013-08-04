package com.akqa.kiev.android.conferer.view.conference.details;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.akqa.kiev.android.conferer.ConferenceDetailsActivity;
import com.akqa.kiev.android.conferer.R;
import com.akqa.kiev.android.conferer.model.ConferenceDetailsData;
import com.akqa.kiev.android.conferer.model.SessionData;
import com.akqa.kiev.android.conferer.utils.DateUtils;
import com.akqa.kiev.android.conferer.web.ConfererService;

public class SessionsFragment extends Fragment {

	public static final String DATE_ARG = "month";

	private List<SessionData> sessions;

	private ConfererService confererService = new ConfererService();

	private Date date;

	private SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM",
			Locale.ENGLISH);

	public void setSessions(List<SessionData> sessions) {
		this.sessions = sessions;
	}

	public SessionsFragment() {
		super();
		dateFormat = new SimpleDateFormat("EEE", Locale.ENGLISH);
		dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		ScrollView rootView = (ScrollView) inflater.inflate(
				R.layout.conferences_fragment, container, false);

		LinearLayout sessionsRoot = (LinearLayout) rootView.getChildAt(0);
		if (sessions == null) {
			ConferenceDetailsData conferenceDetails = confererService
					.loadConferenceDetails(getArguments().getLong(
							ConferenceDetailsActivity.CONFERENCE_ID_ARG));
			Map<Date, List<SessionData>> sortedSessions = DateUtils
					.sortSessionsByDays(conferenceDetails.getSessions());
			sessions = sortedSessions.get(getDate());
		}
		if (sessions != null && !sessions.isEmpty()) {
			for (int i = 0; i < sessions.size(); i++) {
				SessionItemView sessionItemView = new SessionItemView(
						rootView.getContext(), sessions.get(i));
				if (i % 2 == 0) {
					sessionItemView.setBackgroundColor(getResources().getColor(
							R.color.odd_conference_color));
					sessionItemView.getBackground().setAlpha(30);
				}
				sessionsRoot.addView(sessionItemView);
			}
		}
		return rootView;
	}

	private Date getDate() {
		if (date == null) {
			date = new Date(getArguments().getLong(DATE_ARG));
		}
		return date;
	}

	public String getTitle() {
		return dateFormat.format(getDate());
	}

}
