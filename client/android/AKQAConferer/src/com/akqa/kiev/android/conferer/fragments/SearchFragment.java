package com.akqa.kiev.android.conferer.fragments;

import java.util.ArrayList;
import java.util.List;

import com.akqa.kiev.android.conferer.OnSearchResultSelectedListener;
import com.akqa.kiev.android.conferer.R;
import com.akqa.kiev.android.conferer.model.SearchData;
import com.akqa.kiev.android.conferer.service.ConfererDbService;
import com.akqa.kiev.android.conferer.service.ConfererWebService;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class SearchFragment extends Fragment implements OnItemClickListener {
	public static final String ARG_SEARCH_QUERY = "searchQuery";

	private ConfererDbService service;
	private String searchQuery;
	private List<SearchData> searchDataList;
	private ListView searchResultsLV;
	private SearchResultsArrayAdapter adapter;
	private OnSearchResultSelectedListener onResultSelectedListener;

	public static SearchFragment newInstance(String searchQuery) {
		Bundle args = new Bundle();
		args.putString(ARG_SEARCH_QUERY, searchQuery);
		SearchFragment fragment = new SearchFragment();
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		service = new ConfererDbService(getActivity());
		searchDataList = new ArrayList<SearchData>();
		onResultSelectedListener = (OnSearchResultSelectedListener) getActivity();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_search_results, container, false);
		return view;
	}

	@Override
	public void onStart() {
		searchResultsLV = (ListView) getView().findViewById(R.id.search_results_list);
		searchResultsLV.setOnItemClickListener(this);
		adapter = new SearchResultsArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, searchDataList);
		searchResultsLV.setAdapter(adapter);
		super.onStart();
	}

	public String getSearchQuery() {
		return searchQuery;
	}

	public void setSearchQuery(String searchQuery) {
		this.searchQuery = searchQuery;
		SearchTask task = new SearchTask();
		task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, searchQuery);
	}

	private class SearchTask extends AsyncTask<String, Void, List<SearchData>> {

		@Override
		protected List<SearchData> doInBackground(String... params) {
			if (params.length > 0) {
				List<SearchData> searchDataList;
				String query = params[0];
				searchDataList = service.searchConferences(query);
				searchDataList.addAll(service.searchSessions(query));
				searchDataList.addAll(service.searchSpeakers(query));
				return searchDataList;
			} else {
				return null;
			}
		}

		@Override
		protected void onPostExecute(List<SearchData> result) {
			if (result != null) {
				searchDataList.clear();
				searchDataList.addAll(result);
			}
		}

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		SearchData data = searchDataList.get(position);
		onResultSelectedListener.onSearchResultClick(data.getType(), data.getId());
	}

}
