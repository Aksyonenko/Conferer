package com.akqa.kiev.android.conferer.view.conference;

import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.akqa.kiev.android.conferer.R;
import com.akqa.kiev.android.conferer.model.ConferenceData;
import com.akqa.kiev.android.conferer.web.client.ConfererWebClient;
import com.akqa.kiev.android.conferer.web.json.ConferencesJsonParser;
import com.akqa.kiev.android.conferer.web.json.exception.JsonParseException;

public class ConferencesFragment extends Fragment {

	private List<ConferenceData> conferencesForMonth;

	public static final String MONTH_ARG = "monthArg";

	private ConfererWebClient webClient = new ConfererWebClient();

	private ConferencesJsonParser conferencesJsonParser = new ConferencesJsonParser();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		ScrollView rootView = (ScrollView) inflater.inflate(
				R.layout.conferences_fragment, container, false);

		LinearLayout conferencesRoot = (LinearLayout) rootView.getChildAt(0);
		if (conferencesForMonth == null) {
			loadConferencesForMonth();
		}
		if (conferencesForMonth != null && !conferencesForMonth.isEmpty()) {
			for (int i = 0; i < conferencesForMonth.size(); i++) {
				ConferenceView conferenceView = new ConferenceView(
						rootView.getContext(), conferencesForMonth.get(i));
				if (i % 2 == 0) {
					conferenceView.setBackgroundColor(getResources().getColor(
							R.color.odd_conference_color));
					conferenceView.getBackground().setAlpha(30);
				}
				conferencesRoot.addView(conferenceView);
			}
		}
		return rootView;
	}

	private void loadConferencesForMonth() {
		long date = getArguments().getLong(MONTH_ARG);
		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		calendar.setTimeInMillis(date);
		String conferencesJson = webClient.getConferences(
				calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1);
		try {
			conferencesForMonth = conferencesJsonParser
					.parseConferences(conferencesJson);
		} catch (JsonParseException e) {
			Log.e(getClass().getName(), e.getMessage());
		}
	}
}
