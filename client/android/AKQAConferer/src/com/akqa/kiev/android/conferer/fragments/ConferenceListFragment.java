package com.akqa.kiev.android.conferer.fragments;

import java.util.ArrayList;
import java.util.List;

import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.akqa.kiev.android.conferer.OnConferenceSelectedListener;
import com.akqa.kiev.android.conferer.R;
import com.akqa.kiev.android.conferer.model.ConferenceData;
import com.akqa.kiev.android.conferer.service.ConfererDbService;
import com.akqa.kiev.android.conferer.service.ConfererService;
import com.akqa.kiev.android.conferer.service.ConfererWebService;


public class ConferenceListFragment extends Fragment implements OnItemClickListener {
	
	private ConfererService confererService;
	ListView conferenceListView;
	StickyListHeadersListView conferencesStickyListView;
	List<ConferenceData> conferences;

	ConferenceListStickyAdapter stickyAdapter;
	ArrayAdapter<ConferenceData> conferenceLVAdapter;
	OnConferenceSelectedListener conferenceSelectedListener;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		confererService = new ConfererDbService(getActivity());
		conferences = new ArrayList<ConferenceData>();
		conferenceSelectedListener = (OnConferenceSelectedListener) getActivity();
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
		stickyAdapter = new ConferenceListStickyAdapter(getActivity(), conferences);
		conferencesStickyListView = (StickyListHeadersListView) getView().findViewById(R.id.sticky_conference_list_view);
		conferencesStickyListView.setAdapter(stickyAdapter);
		conferencesStickyListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		conferencesStickyListView.setOnItemClickListener(this);
		LoadDataTask loadDataTask = new LoadDataTask();
		loadDataTask.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
	}
	
	private class LoadDataTask extends AsyncTask<Void, Void, List<ConferenceData>> {
		@Override
		protected List<ConferenceData> doInBackground(Void... params) {
			List<Long> months = confererService.loadConferencesMonths();
			List<ConferenceData> conferences = new ArrayList<ConferenceData>();
			if(months != null && !months.isEmpty()) {
				for(Long month : months) {
					conferences.addAll(confererService.loadConferencesForMonth(month));
				}
				return conferences;
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(List<ConferenceData> result) {
			conferences.clear();
			for(ConferenceData data : result) {
				conferences.add(data);
			}
			stickyAdapter.notifyDataSetChanged();
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Long clickedId  = conferences.get(position).getId();
		conferencesStickyListView.setItemChecked(position, true);
		conferenceSelectedListener.onConferenceSelected(clickedId);
	}

}
