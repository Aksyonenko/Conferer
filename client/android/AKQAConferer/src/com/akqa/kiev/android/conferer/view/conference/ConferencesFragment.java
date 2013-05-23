package com.akqa.kiev.android.conferer.view.conference;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.akqa.kiev.android.conferer.R;

public class ConferencesFragment extends Fragment {

	/**
	 * The fragment argument representing the section number for this fragment.
	 */
	public static final String ARG_SECTION_NUMBER = "section_number";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		ScrollView rootView = (ScrollView) inflater.inflate(
				R.layout.conferences_fragment, container, false);

		LinearLayout conferencesRoot = (LinearLayout) rootView.getChildAt(0);

		//TODO stub, will be removed later
		for (int i = 0; i < 7; i++) {
			ConferenceView conferenceView = new ConferenceView(
					rootView.getContext());
			if (i % 2 == 0) {
				conferenceView.setBackgroundColor(getResources().getColor(
						R.color.odd_conference_color));
				conferenceView.getBackground().setAlpha(30);
			}
			conferencesRoot.addView(conferenceView);
		}

		return rootView;
	}

}
