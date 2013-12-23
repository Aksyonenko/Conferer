package com.akqa.kiev.android.conferer.fragments;

import java.util.List;

import com.akqa.kiev.android.conferer.R;
import com.akqa.kiev.android.conferer.model.CategoryData;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class NavigationDrawerCategoryAdapter extends BaseAdapter {

	private List<CategoryData> categories;
	private LayoutInflater inflater;
	
	
	public NavigationDrawerCategoryAdapter(Context context, List<CategoryData> categories) {
		inflater = LayoutInflater.from(context); 
		this.categories = categories;
	}
	
	@Override
	public int getCount() {
		return categories.size();
	}

	@Override
	public Object getItem(int position) {
		return categories.get(position);
	}

	@Override
	public long getItemId(int position) {
		return categories.get(position).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		if(row == null) {
			row = inflater.inflate(R.layout.drawer_item, parent, false);
		}
		TextView text  = (TextView) row.findViewById(R.id.drawer_item_text);
		text.setText(categories.get(position).getName());
		return row;
	}

}
