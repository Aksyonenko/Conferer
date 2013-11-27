package com.akqa.kiev.android.conferer.view.conference;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.akqa.kiev.android.conferer.R;
import com.akqa.kiev.android.conferer.model.ConferenceData;
import com.akqa.kiev.android.conferer.service.ConfererWebService;
import com.akqa.kiev.android.conferer.utils.DateUtils;

public class ConferencesFragment extends Fragment {

	private List<ConferenceData> conferencesForMonth;

	public static final String MONTH_ARG = "month";

	private ConfererWebService confererService = new ConfererWebService();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		ScrollView rootView = (ScrollView) inflater.inflate(
				R.layout.conferences_fragment, container, false);

		LinearLayout conferencesRoot = (LinearLayout) rootView.getChildAt(0);
		if (conferencesForMonth == null) {
			List<Long> months = confererService.loadConferencesMonths();
			int month = DateUtils.getNearestToCurrentDateIndex(months);
			conferencesForMonth = confererService
					.loadConferencesForMonth(month);
			
		}
		if (conferencesForMonth != null && !conferencesForMonth.isEmpty()) {
			for (int i = 0; i < conferencesForMonth.size(); i++) {
				ConferenceItemView conferenceItemView = new ConferenceItemView(
						rootView.getContext(), conferencesForMonth.get(i));
				if (i % 2 == 0) {
					conferenceItemView.setBackgroundColor(getResources()
							.getColor(R.color.odd_conference_color));
					conferenceItemView.getBackground().setAlpha(30);
				}
				conferencesRoot.addView(conferenceItemView);
			}
		}
		return rootView;
	}
}
