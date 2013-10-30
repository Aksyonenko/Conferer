package com.akqa.kiev.android.conferer.fragments;

import java.util.ArrayList;
import java.util.List;

import com.akqa.kiev.android.conferer.R;
import com.akqa.kiev.android.conferer.model.ConferenceData;
import com.akqa.kiev.android.conferer.web.ConfererService;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ConferenceListFragment extends Fragment {
	
	private ConfererService confererService;
	ListView conferenceListView;
	List<String> conferenceNames;
	ArrayAdapter conferenceLVAdapter;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		confererService = new ConfererService();
		conferenceNames = new ArrayList<String>();
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
		List<Long> months = confererService.loadConferencesMonths();
		conferenceListView = (ListView) getView().findViewById(R.id.conferenceListView);
		conferenceLVAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, conferenceNames);
		conferenceListView.setAdapter(conferenceLVAdapter);
		loadDataTask.execute();
	}
	
	AsyncTask<Void, Void, List<ConferenceData>> loadDataTask = new AsyncTask<Void, Void, List<ConferenceData>>() {
		
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
				conferenceNames.add(data.getTitle());
			}
			conferenceLVAdapter.notifyDataSetChanged();
		}
	};
}
