package com.akqa.kiev.android.conferer.fragments;

import java.util.List;

import com.akqa.kiev.android.conferer.R;
import com.akqa.kiev.android.conferer.model.SearchData;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class SearchResultsArrayAdapter extends ArrayAdapter<SearchData> {
	
	private List<SearchData> data;
	
	public SearchResultsArrayAdapter(Context context, int textViewResourceId, List<SearchData> data) {
		super(context, textViewResourceId, data);
		this.data = data;
	}
	
	@Override
	public int getCount() {
		return data.size();
	}
	
	@Override
	public SearchData getItem(int position) {
		return data.get(position);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		if (row == null) {
			LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(
					Context.LAYOUT_INFLATER_SERVICE);
			row = inflater.inflate(R.layout.search_results_list_item, parent, false);
		}
		SearchData item = getItem(position);
		TextView title = (TextView) row.findViewById(R.id.search_resluts_list_item_title);
		title.setText(item.getTitle());
		TextView subtitle = (TextView) row.findViewById(R.id.search_results_list_item_subtitle);
		subtitle.setText(item.getSubtitle());
		return row;
	}
	
}
