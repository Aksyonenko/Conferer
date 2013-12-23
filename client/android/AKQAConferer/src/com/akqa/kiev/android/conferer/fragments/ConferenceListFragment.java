package com.akqa.kiev.android.conferer.fragments;

import java.util.ArrayList;
import java.util.List;

import se.emilsjolander.stickylistheaders.StickyListHeadersListView;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
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
import com.akqa.kiev.android.conferer.utils.Constants;

public class ConferenceListFragment extends Fragment implements OnItemClickListener {

	public static final long CATEGORY_ALL = 0L;

	private ConfererDbService confererService;
	ListView conferenceListView;
	StickyListHeadersListView conferencesStickyListView;
	List<ConferenceData> conferences;

	ConferenceListStickyAdapter stickyAdapter;
	ArrayAdapter<ConferenceData> conferenceLVAdapter;
	OnConferenceSelectedListener conferenceSelectedListener;
	
	private long categoryId = CATEGORY_ALL;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		confererService = new ConfererDbService(getActivity());
		conferences = new ArrayList<ConferenceData>();
		conferenceSelectedListener = (OnConferenceSelectedListener) getActivity();
		if(savedInstanceState != null) {
			Long categoryId = savedInstanceState.getLong(Constants.BUNDLE_CATEGORY_ID);
			if(categoryId != null) {
				this.categoryId = categoryId;
			}
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_conference_list, container, false);
		return view;
	}

	@Override
	public void onStart() {
		super.onStart();
		stickyAdapter = new ConferenceListStickyAdapter(getActivity(), conferences);
		conferencesStickyListView = (StickyListHeadersListView) getView()
				.findViewById(R.id.sticky_conference_list_view);
		conferencesStickyListView.setAdapter(stickyAdapter);
		conferencesStickyListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		conferencesStickyListView.setOnItemClickListener(this);
		setCategory(categoryId);
	}

	public void setCategory(long categoryId) {
		LoadDataTask loadDataTask = new LoadDataTask();
		loadDataTask.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, categoryId);
	}

	private class LoadDataTask extends AsyncTask<Long, Void, List<ConferenceData>> {
		@Override
		protected List<ConferenceData> doInBackground(Long... params) {
			List<ConferenceData> conferences = new ArrayList<ConferenceData>();
			if (params.length < 1 || params[0] == null || params[0] == CATEGORY_ALL) {
				List<Long> months = confererService.loadConferencesMonths();
				if (months != null && !months.isEmpty()) {
					for (Long month : months) {
						conferences.addAll(confererService.loadConferencesForMonth(month));
					}
					return conferences;
				}
			} else {
				conferences = confererService.loadConferencesForCategory(params[0]);
				return conferences;
			}
			return null;
		}

		@Override
		protected void onPostExecute(List<ConferenceData> result) {
			conferences.clear();
			for (ConferenceData data : result) {
				Log.i(getClass().getName(), "Conference category: " + data.getCategory().getId() + ": "
						+ data.getCategory().getName());
				conferences.add(data);
			}
			stickyAdapter.notifyDataSetChanged();
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Long clickedId = conferences.get(position).getId();
		conferencesStickyListView.setItemChecked(position, true);
		conferenceSelectedListener.onConferenceSelected(clickedId);
	}

}
