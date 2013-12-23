package com.akqa.kiev.android.conferer.fragments;

import java.util.ArrayList;
import java.util.List;

import com.akqa.kiev.android.conferer.DrawerFragmentListener;
import com.akqa.kiev.android.conferer.R;
import com.akqa.kiev.android.conferer.model.CategoryData;
import com.akqa.kiev.android.conferer.service.ConfererDbService;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class NavigationDrawerFragment extends Fragment {
	private ConfererDbService service;
	private List<CategoryData> categories;
	private ListView categoriesListView;
	private NavigationDrawerCategoryAdapter drawerListAdapter;
	private DrawerFragmentListener listener;
	private long categoryId;
	private View categoryResetView;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		service = new ConfererDbService(getActivity());
		categories = new ArrayList<CategoryData>();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.drawer, container, false);
	}
	
	@Override
	public void onStart() {
		super.onStart();
		categoriesListView = (ListView) getView().findViewById(R.id.navigation_drawer_list);
		drawerListAdapter = new NavigationDrawerCategoryAdapter(getActivity(), categories);
		categoriesListView.setAdapter(drawerListAdapter);
		categoriesListView.setOnItemClickListener(new DrawerCategoryListOnItemClickListener());
		categoriesListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		categoryResetView = getView().findViewById(R.id.navigation_drawer_category_reset_layout);
		categoryResetView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(listener != null) {
					categoriesListView.clearChoices();
					drawerListAdapter.notifyDataSetChanged();
					categoryResetView.setSelected(true);
					listener.onCategorySelected(ConferenceListFragment.CATEGORY_ALL);
				}
			}
		});
		LoadDataTask task = new LoadDataTask();
		task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
		
		
	}
	
	public void setListener(DrawerFragmentListener listener) {
		this.listener = listener;
	}
	
	private class DrawerCategoryListOnItemClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int postion, long id) {
			if(listener != null) {
				view.setSelected(true);
				listener.onCategorySelected(categories.get(postion).getId());
				categoryResetView.setSelected(false);
			}
		}
		
	}
	
	private class LoadDataTask extends AsyncTask<Void, Void, List<CategoryData>> {

		@Override
		protected List<CategoryData> doInBackground(Void... params) {
			List<CategoryData> data = service.loadCategories();
			return data;
		}
		
		@Override
		protected void onPostExecute(List<CategoryData> result) {
			if(result != null && !isDetached()) {
				categories.clear();
				for(CategoryData item : result) {
					categories.add(item);
				}
				drawerListAdapter.notifyDataSetChanged();
			}
		}
		
	}
}
