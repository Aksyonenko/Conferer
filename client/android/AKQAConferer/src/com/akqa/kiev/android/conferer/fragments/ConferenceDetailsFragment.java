package com.akqa.kiev.android.conferer.fragments;

import java.util.ArrayList;
import java.util.List;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.akqa.kiev.android.conferer.OnDetailsFragmentStartedListener;
import com.akqa.kiev.android.conferer.R;
import com.akqa.kiev.android.conferer.model.ConferenceData;
import com.akqa.kiev.android.conferer.model.ConferenceDetailsData;
import com.akqa.kiev.android.conferer.model.SessionData;
import com.akqa.kiev.android.conferer.web.ConfererService;

public class ConferenceDetailsFragment extends Fragment {
	private ConfererService confererService;
	private Long conferenceId;
	List<SessionData> sessions;
	ListView conferenceDetailsListView;
	ConferenceDetailsArrayAdapter conferenceDetailsAdapter;
	OnDetailsFragmentStartedListener fragmentStartedListener;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		confererService = new ConfererService();
		sessions = new ArrayList<SessionData>();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_conference_details, container, false);
		return view;
	}
	
	@Override
	public void onStart() {
		super.onStart();
		fragmentStartedListener = (OnDetailsFragmentStartedListener) getActivity();
		conferenceDetailsListView = (ListView) getView().findViewById(R.id.conference_details_list_view);
		conferenceDetailsAdapter = new ConferenceDetailsArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, sessions);
		conferenceDetailsListView.setAdapter(conferenceDetailsAdapter);
		fragmentStartedListener.onDetailsFragmentStarted(this);
		
	}

	public void setConferenceId(Long conferenceId) {
		this.conferenceId = conferenceId;
		onConferenceIdChange(conferenceId);
	}
	
	private void onConferenceIdChange(Long conferenceId) {
		LoadDataTask loadDataTask = new LoadDataTask();
		loadDataTask.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
	}
	
	private void updateViews(ConferenceDetailsData conferenceDetailsData) {
		sessions.clear();
		for(SessionData data : conferenceDetailsData.getSessions()) {
			sessions.add(data);
		}
		conferenceDetailsAdapter.notifyDataSetChanged();
	}
	
	private class LoadDataTask extends AsyncTask<Void, Void, ConferenceDetailsData> {

		@Override
		protected ConferenceDetailsData doInBackground(Void... params) {
			return confererService.loadConferenceDetails(conferenceId);
		}
		
		@Override
		protected void onPostExecute(ConferenceDetailsData result) {
			if(result != null) {
				updateViews(result);
			}
		}
		
	}
}
