package com.akqa.kiev.android.conferer.fragments;

import java.text.SimpleDateFormat;
import java.util.Iterator;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.akqa.kiev.android.conferer.OnSessionDetailsFragmentStartedListenter;
import com.akqa.kiev.android.conferer.R;
import com.akqa.kiev.android.conferer.model.SessionData;
import com.akqa.kiev.android.conferer.model.SpeakerData;
import com.akqa.kiev.android.conferer.service.ConfererService;
import com.akqa.kiev.android.conferer.service.ConfererWebService;
import com.androidquery.AQuery;

public class SessionDetailsFragment extends Fragment {
	private ConfererService confererService;
	private Long sessionId, conferenceId;
	private OnSessionDetailsFragmentStartedListenter onFragmentStartedListenter;
	private SimpleDateFormat sessionTimeFormat;
	private SimpleDateFormat sessionDateFormat;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		confererService = new ConfererWebService();
		sessionTimeFormat = new SimpleDateFormat("HH:mm");
		sessionDateFormat = new SimpleDateFormat("dd MMMM yyyy, EEE");
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_session_details, container, false);
		return view;
	}
	@Override
	public void onStart() {
		super.onStart();
		onFragmentStartedListenter = (OnSessionDetailsFragmentStartedListenter) getActivity();
		onFragmentStartedListenter.onSessionDetailsFragmentStarted(this);
	}
	
	public void setSessionId(Long sessionId, Long conferenceId) {
		this.sessionId = sessionId;
		onSessionIdChange(sessionId);
	}
	
	private void onSessionIdChange(Long sessionId) {
		LoadDataTask loadDataTask = new LoadDataTask();
		loadDataTask.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
	}
	
	private void updateViews(SessionData sessionData) {
		TextView sessionTitle = (TextView) getView().findViewById(R.id.session_details_title);
		sessionTitle.setText(sessionData.getTitle());
		ImageView sessionLogo = (ImageView) getView().findViewById(R.id.session_details_logo);
		if(sessionLogo != null) {
			AQuery aq = new AQuery(getView());
			aq.id(R.id.session_details_logo).image(sessionData.getSessionLogoUrl());
		}
		TextView sessionSpeaker = (TextView) getView().findViewById(R.id.session_details_speaker);
		StringBuilder builder = new StringBuilder();
		Iterator<SpeakerData> speakers = sessionData.getSpeakers().iterator();
		while (speakers.hasNext()) {
			SpeakerData speaker = speakers.next();
			builder.append(speaker.getFirstName()).append(" ").append(speaker.getLastName());
			if (speakers.hasNext()) {
				builder.append(", ");
			}
		}
		sessionSpeaker.setText(builder.toString());
		String sessionTimeString = sessionTimeFormat.format(sessionData.getStartTime()) + " - "
				+ sessionTimeFormat.format(sessionData.getEndTime());
		TextView sessionTime = (TextView) getView().findViewById(R.id.session_details_time);
		sessionTime.setText(sessionTimeString);
		TextView sessionLocation = (TextView) getView().findViewById(R.id.session_details_location);
		sessionLocation.setText(sessionData.getLocation());
		TextView sessionDate = (TextView) getView().findViewById(R.id.session_details_day);
		sessionDate.setText(sessionDateFormat.format(sessionData.getStartTime()));
		TextView sessionDescription = (TextView) getView().findViewById(R.id.session_details_description);
		sessionDescription.setText(sessionData.getSummary());
}
	
	private class LoadDataTask extends AsyncTask<Void, Void, SessionData> {

		@Override
		protected SessionData doInBackground(Void... params) {
			return confererService.loadSessionDetails(sessionId);
		}
		
		@Override
		protected void onPostExecute(SessionData result) {
			if(result != null) {
				updateViews(result);
			}
		}
	}
}
