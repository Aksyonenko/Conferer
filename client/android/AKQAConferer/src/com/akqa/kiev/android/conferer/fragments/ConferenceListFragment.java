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

import com.akqa.kiev.android.conferer.R;
import com.akqa.kiev.android.conferer.model.ConferenceData;
import com.akqa.kiev.android.conferer.service.ConfererWebService;

public class ConferenceListFragment extends Fragment {
	
	private ConfererWebService confererService;
	ListView conferenceListView;
	List<ConferenceData> conferences;
	ArrayAdapter<ConferenceData> conferenceLVAdapter;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		confererService = new ConfererWebService();
		conferences = new ArrayList<ConferenceData>();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_conference_list, container, false);
		return view;
	}
	
	@Override
	public void onStart() {
		super.onStart();
		conferenceListView = (ListView) getView().findViewById(R.id.conferenceListView);
		conferenceLVAdapter = new ConferenceListArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, conferences);
		conferenceListView.setAdapter(conferenceLVAdapter);
		LoadDataTask loadDataTask = new LoadDataTask();
		loadDataTask.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
	}
	
	private class LoadDataTask extends AsyncTask<Void, Void, List<ConferenceData>> {
		@Override
		protected List<ConferenceData> doInBackground(Void... params) {
			List<Long> months = confererService.loadConferencesMonths();
			if(months != null && !months.isEmpty()) {
				return confererService.loadConferencesForMonth(months.get(0));
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(List<ConferenceData> result) {
			for(ConferenceData data : result) {
				conferences.add(data);
			}
			conferenceLVAdapter.notifyDataSetChanged();
		}
	}

}
