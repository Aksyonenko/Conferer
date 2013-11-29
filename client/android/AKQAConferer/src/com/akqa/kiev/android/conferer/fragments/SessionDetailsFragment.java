package com.akqa.kiev.android.conferer.fragments;

import com.akqa.kiev.android.conferer.OnSessionDetailsFragmentStartedListenter;
import com.akqa.kiev.android.conferer.R;
import com.akqa.kiev.android.conferer.model.SessionData;
import com.akqa.kiev.android.conferer.service.ConfererService;
import com.akqa.kiev.android.conferer.service.ConfererWebService;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class SessionDetailsFragment extends Fragment {
	private ConfererService confererService;
	private SessionData sessionData;
	private Long sessionId;
	private OnSessionDetailsFragmentStartedListenter onFragmentStartedListenter;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		confererService = new ConfererWebService();
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
	
	public void setSessionId(Long sessionId) {
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
